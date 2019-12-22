package guru.bug.austras.apt.events.process;

import guru.bug.austras.apt.core.engine.AustrasProcessorPlugin;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.codegen.TemplateException;
import guru.bug.austras.events.Message;
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
    private DispatcherGenerator dispatcherGenerator;
    private BroadcasterGenerator broadcasterGenerator;

    @Override
    public void process(ProcessingContext ctx) {
        this.ctx = ctx;
        try {
            dispatcherGenerator = new DispatcherGenerator(ctx);
            broadcasterGenerator = new BroadcasterGenerator(ctx);
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }
        ctx.roundEnv().getElementsAnnotatedWith(Message.class)
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
            if (e.getKind() != ElementKind.PARAMETER || (e.getEnclosingElement().getKind() != ElementKind.METHOD && e.getEnclosingElement().getKind() != ElementKind.CONSTRUCTOR)) {
                logError(ctx, e, "Message annotation unexpected placement"); // TODO Better error handling
                return null;
            }
            ExecutableElement method = (ExecutableElement) e.getEnclosingElement();
            if (isValidBroadcaster(method) && ctx.modelUtils().isBroadcaster(e.asType())) {
                generateBroadcaster(e);
            }

            return null;
        }


        @Override
        public Void visitExecutable(ExecutableElement e, ProcessingContext ctx) {
            if (isValidReceiver(ctx, e)) {
                generateDispatcher(e);
            }

            return null;
        }

        private boolean isValidReceiver(ProcessingContext ctx, ExecutableElement method) {
            var methodIsAnnotated = method.getAnnotationsByType(Message.class).length > 0;
            if (!methodIsAnnotated) {
                return false;
            }
            var parameters = method.getParameters();
            if (parameters.size() > 1) {
                logError(ctx, method, "Too many parameters");
                return false;
            }
            return true;
        }

        private boolean isValidBroadcaster(ExecutableElement method) {
            var annotatedCount = method.getParameters().stream()
                    .filter(p -> p.getAnnotationsByType(Message.class).length > 0)
                    .filter(p -> ctx.modelUtils().isBroadcaster(p.asType()))
                    .count();

            return annotatedCount >= 1;
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

        private void generateDispatcher(ExecutableElement e) {
            dispatcherGenerator.generate(e);
        }
    }
}
