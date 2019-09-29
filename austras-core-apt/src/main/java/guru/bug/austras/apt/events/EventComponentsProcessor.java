package guru.bug.austras.apt.events;

import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.core.componentmap.UniqueNameGenerator;
import guru.bug.austras.engine.AustrasProcessorPlugin;
import guru.bug.austras.engine.ProcessingContext;
import guru.bug.austras.events.Message;

import javax.lang.model.element.*;
import javax.lang.model.util.SimpleElementVisitor9;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventComponentsProcessor implements AustrasProcessorPlugin {
    private static final Logger log = Logger.getLogger(EventComponentsProcessor.class.getName());
    private final ElementWithMessageVisitor msgVisitor = new ElementWithMessageVisitor();
    private ModelUtils modelUtils;
    private UniqueNameGenerator uniqueNameGenerator = new UniqueNameGenerator();
    private ReceiverGenerator receiverGenerator;
    private BroadcasterGenerator broadcasterGenerator;

    @Override
    public void process(ProcessingContext ctx) {
        modelUtils = new ModelUtils(uniqueNameGenerator, ctx.processingEnv());
        receiverGenerator = new ReceiverGenerator(modelUtils);
        broadcasterGenerator = new BroadcasterGenerator(modelUtils);
        ctx.roundEnv().getElementsAnnotatedWith(Message.class)
                .forEach(e -> e.accept(msgVisitor, ctx));
    }

    private boolean isValidReceiver(ProcessingContext ctx, ExecutableElement method) {
        var annotatedCount = method.getParameters().stream()
                .filter(p -> p.getAnnotationsByType(Message.class).length > 0)
                .filter(p -> !modelUtils.isBroadcaster(p.asType()))
                .count();

        if (annotatedCount > 1) {
            logError(ctx, method, "Expecting only single receiver parameter per method, but there are " + annotatedCount); // TODO Better error handling
            return false;
        }

        boolean annotatedMethod = method.getAnnotationsByType(Message.class).length > 0;

        if (annotatedMethod && annotatedCount > 0) {
            logError(ctx, method, "Message annotation on method and on parameter together not supported"); // TODO Better error handling
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


    private void logError(ProcessingContext ctx, Element e) {
        logError(ctx, e, null, null);
    }

    private void logError(ProcessingContext ctx, Element e, Throwable t) {
        logError(ctx, e, null, t);
    }

    private void logError(ProcessingContext ctx, Element e, String reason) {
        logError(ctx, e, reason, null);
    }

    private void logError(ProcessingContext ctx, Element e, String reason, Throwable t) {
        String msg = "Cannot process " + e + (reason == null ? "" : ": " + reason);
        log.log(Level.SEVERE, msg, t);
        ctx.processingEnv().getMessager().printMessage(Diagnostic.Kind.ERROR, msg, e);
    }

    private void generateBroadcaster(ProcessingContext ctx, VariableElement e) {
        try {
            broadcasterGenerator.generate(ctx, e);
        } catch (IOException ex) {
            logError(ctx, e, "Code generation exception", ex); // TODO Better error handling
        }
    }

    private void generateReceiver(ProcessingContext ctx, VariableElement e) {
        generateReceiver(ctx, (ExecutableElement) e.getEnclosingElement());
    }

    private void generateReceiver(ProcessingContext ctx, ExecutableElement e) {
        try {
            receiverGenerator.generate(ctx, e);
        } catch (IOException ex) {
            logError(ctx, e, "Code generation exception", ex); // TODO Better error handling
        }
    }

    private class ElementWithMessageVisitor extends SimpleElementVisitor9<Void, ProcessingContext> {

        @Override
        protected Void defaultAction(Element e, ProcessingContext ctx) {
            logError(ctx, e);
            return super.defaultAction(e, ctx);
        }

        @Override
        public Void visitType(TypeElement e, ProcessingContext ctx) {
            // ignoring Message annotation on class, as it is already generated receiver
            return null;
        }

        @Override
        public Void visitVariable(VariableElement e, ProcessingContext ctx) {
            if (e.getKind() != ElementKind.PARAMETER || (e.getEnclosingElement().getKind() != ElementKind.METHOD && e.getEnclosingElement().getKind() != ElementKind.CONSTRUCTOR)) {
                logError(ctx, e, "Message annotation unexpected placement"); // TODO Better error handling
                return null;
            }
            ExecutableElement method = (ExecutableElement) e.getEnclosingElement();
            if (!isValidReceiver(ctx, method) && !isValidBroadcaster(method)) {
                return null;
            }

            if (modelUtils.isBroadcaster(e.asType())) {
                generateBroadcaster(ctx, e);
            } else {
                generateReceiver(ctx, e);
            }

            return null;
        }


        @Override
        public Void visitExecutable(ExecutableElement e, ProcessingContext ctx) {
            if (isValidReceiver(ctx, e)) {
                generateReceiver(ctx, e);
            }

            return null;
        }
    }
}
