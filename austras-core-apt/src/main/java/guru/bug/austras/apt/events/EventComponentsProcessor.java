package guru.bug.austras.apt.events;

import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.core.componentmap.UniqueNameGenerator;
import guru.bug.austras.events.Message;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.SimpleElementVisitor9;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class EventComponentsProcessor extends AbstractProcessor {
    private static final Logger log = Logger.getLogger(EventComponentsProcessor.class.getName());
    private final ElementWithMessageVisitor msgVisitor = new ElementWithMessageVisitor();
    private ModelUtils modelUtils;
    private UniqueNameGenerator uniqueNameGenerator = new UniqueNameGenerator();
    private ReceiverGenerator receiverGenerator;
    private BroadcasterGenerator broadcasterGenerator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        modelUtils = new ModelUtils(uniqueNameGenerator, processingEnv);
        receiverGenerator = new ReceiverGenerator(processingEnv, modelUtils);
        broadcasterGenerator = new BroadcasterGenerator(processingEnv, modelUtils);
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

    private boolean isValidReceiver(ExecutableElement method) {
        var annotatedCount = method.getParameters().stream()
                .filter(p -> p.getAnnotationsByType(Message.class).length > 0)
                .filter(p -> !modelUtils.isBroadcaster(p.asType()))
                .count();

        if (annotatedCount > 1) {
            logError(method, "Expecting only single receiver parameter per method, but there are " + annotatedCount); // TODO Better error handling
            return false;
        }

        boolean annotatedMethod = method.getAnnotationsByType(Message.class).length > 0;

        if (annotatedMethod && annotatedCount > 0) {
            logError(method, "Message annotation on method and on parameter together not supported"); // TODO Better error handling
            return false;
        }

        return true;
    }

    private boolean isValidBroadcaster(ExecutableElement method) {
        var annotatedCount = method.getParameters().stream()
                .filter(p -> p.getAnnotationsByType(Message.class).length > 0)
                .filter(p -> modelUtils.isBroadcaster(p.asType()))
                .count();

        return annotatedCount >= 1;
    }


    private void logError(Element e) {
        logError(e, null, null);
    }

    private void logError(Element e, Throwable t) {
        logError(e, null, t);
    }

    private void logError(Element e, String reason) {
        logError(e, reason, null);
    }

    private void logError(Element e, String reason, Throwable t) {
        String msg = "Cannot process " + e + (reason == null ? "" : ": " + reason);
        log.log(Level.SEVERE, msg, t);
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, e);
    }

    private void generateBroadcaster(VariableElement e) {
        try {
            broadcasterGenerator.generate(e);
        } catch (IOException ex) {
            logError(e, "Code generation exception", ex); // TODO Better error handling
        }
    }

    private void generateReceiver(VariableElement e) {
        generateReceiver((ExecutableElement) e.getEnclosingElement());
    }

    private void generateReceiver(ExecutableElement e) {
        try {
            receiverGenerator.generate(e);
        } catch (IOException ex) {
            logError(e, "Code generation exception", ex); // TODO Better error handling
        }
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

        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            if (e.getKind() != ElementKind.PARAMETER || (e.getEnclosingElement().getKind() != ElementKind.METHOD && e.getEnclosingElement().getKind() != ElementKind.CONSTRUCTOR)) {
                logError(e, "Message annotation unexpected placement"); // TODO Better error handling
                return null;
            }
            ExecutableElement method = (ExecutableElement) e.getEnclosingElement();
            if (!isValidReceiver(method) && !isValidBroadcaster(method)) {
                return null;
            }

            if (modelUtils.isBroadcaster(e.asType())) {
                generateBroadcaster(e);
            } else {
                generateReceiver(e);
            }

            return null;
        }


        @Override
        public Void visitExecutable(ExecutableElement e, Void aVoid) {
            if (isValidReceiver(e)) {
                generateReceiver(e);
            }

            return null;
        }
    }
}
