package guru.bug.austras.apt.events.process;

import guru.bug.austras.apt.core.engine.AustrasProcessorPlugin;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.codegen.TemplateException;
import guru.bug.austras.core.qualifiers.Broadcast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.*;
import javax.lang.model.util.SimpleElementVisitor9;
import javax.tools.Diagnostic;
import java.io.IOException;

public class EventComponentsProcessorPlugin implements AustrasProcessorPlugin {
    private static final Logger log = LoggerFactory.getLogger(EventComponentsProcessorPlugin.class);
    private final ElementWithMessageVisitor msgVisitor = new ElementWithMessageVisitor();
    private ProcessingContext ctx;
    private BroadcasterGenerator broadcasterGenerator;

    @Override
    public void process(ProcessingContext ctx) {
        this.ctx = ctx;
        try {
            broadcasterGenerator = new BroadcasterGenerator(ctx);
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }
        ctx.roundEnv().getElementsAnnotatedWith(Broadcast.class)
                .forEach(e -> e.accept(msgVisitor, ctx));
    }

    @SuppressWarnings("squid:MaximumInheritanceDepth")
    private class ElementWithMessageVisitor extends SimpleElementVisitor9<Void, ProcessingContext> {

        @Override
        protected Void defaultAction(Element e, ProcessingContext ctx) {
            logError(ctx, e);
            return super.defaultAction(e, ctx);
        }

        @Override
        public Void visitType(TypeElement e, ProcessingContext ctx) {
            // ignoring Message annotation on class, as it is already generated dispatcher
            return null;
        }

        @Override
        public Void visitVariable(VariableElement e, ProcessingContext ctx) {
            if (!validParameter(e)
                    || !validConstructor(e.getEnclosingElement())
                    || !validClass(e.getEnclosingElement().getEnclosingElement())) {
                logError(ctx, e, Broadcast.class.getSimpleName() + "-annotation unexpected placement"); // TODO Better error handling
                return null;
            }
            generateBroadcaster(e);
            return null;
        }

        private boolean validParameter(VariableElement e) {
            return e.getKind() == ElementKind.PARAMETER;
        }

        private boolean validConstructor(Element suspect) {
            if (suspect.getKind() != ElementKind.CONSTRUCTOR) {
                return false;
            }
            var constructor = (ExecutableElement) suspect;
            if (!constructor.getThrownTypes().isEmpty()) {
                return false;
            }
            var modifiers = constructor.getModifiers();
            return modifiers.contains(Modifier.PUBLIC);
        }

        private boolean validClass(Element suspect) {
            if (suspect.getKind() != ElementKind.CLASS) {
                return false;
            }
            var type = (TypeElement) suspect;
            var modifiers = type.getModifiers();
            if (modifiers.contains(Modifier.ABSTRACT)) {
                return false;
            }
            return modifiers.contains(Modifier.PUBLIC);
        }

        private void logError(ProcessingContext ctx, Element e, String reason) {
            String msg = "Cannot process " + e + (reason == null ? "" : ": " + reason);
            log.error(msg);
            ctx.processingEnv().getMessager().printMessage(Diagnostic.Kind.ERROR, msg, e);
        }

        private void logError(ProcessingContext ctx, Element e) {
            logError(ctx, e, null);
        }

        private void generateBroadcaster(VariableElement e) {
            broadcasterGenerator.generate(e);
        }
    }
}
