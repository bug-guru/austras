package guru.bug.austras.apt.events;

import guru.bug.austras.apt.core.AbstractAustrasAnnotationProcessor;
import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.core.componentmap.UniqueNameGenerator;
import guru.bug.austras.events.Message;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.SimpleElementVisitor9;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

public class ReceiverProcessor extends AbstractAustrasAnnotationProcessor {
    private final ElementWithMessageVisitor msgVisitor = new ElementWithMessageVisitor();
    private ModelUtils modelUtils;
    private UniqueNameGenerator uniqueNameGenerator = new UniqueNameGenerator();
    private ReceiverGenerator receiverGenerator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        modelUtils = new ModelUtils(this, uniqueNameGenerator, processingEnv);
        receiverGenerator = new ReceiverGenerator(processingEnv, modelUtils);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Message.class.getName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(Message.class)
                .forEach(e -> e.accept(msgVisitor, null));
        return false;
    }

    private class ElementWithMessageVisitor extends SimpleElementVisitor9<Void, Void> {

        @Override
        protected Void defaultAction(Element e, Void aVoid) {
            logError(e);
            return super.defaultAction(e, aVoid);
        }

        @Override
        public Void visitType(TypeElement e, Void aVoid) {
            // ignoring Message annotation on class, as it is already generated receiver
            return null;
        }

        private void logError(Element e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Cannot process " + e, e);
        }

        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            if (e.getKind() != ElementKind.PARAMETER || e.getEnclosingElement().getKind() != ElementKind.METHOD) {
                logError(e); // TODO Better error handling
                return null;
            }
            ExecutableElement method = (ExecutableElement) e.getEnclosingElement();
            return visitExecutable(method, aVoid);
        }

        @Override
        public Void visitExecutable(ExecutableElement e, Void aVoid) {
            if (e.getKind() != ElementKind.METHOD) {
                logError(e); // TODO Better error handling
                return null;
            }
            var annotatedCount = e.getParameters().stream()
                    .filter(p -> p.getAnnotationsByType(Message.class).length > 0)
                    .filter(p -> !modelUtils.isBroadcaster(p.asType()))
                    .count();

            if (annotatedCount > 1) {
                logError(e); // TODO Better error handling
                return null;
            }

            boolean annotatedMethod = e.getAnnotationsByType(Message.class).length > 0;

            if (annotatedMethod && annotatedCount > 0) {
                logError(e); // TODO Better error handling
                return null;
            }

            try {
                receiverGenerator.generate(e);
            } catch (IOException ex) {
                logError(e); // TODO Better error handling
            }
            return null;
        }
    }
}
