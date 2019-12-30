package guru.bug.austras.web.apt;

import guru.bug.austras.apt.core.engine.AustrasProcessorPlugin;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.codegen.TemplateException;
import guru.bug.austras.web.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;
import java.io.IOException;

public class RestServicesProcessor implements AustrasProcessorPlugin {
    private static final Logger log = LoggerFactory.getLogger(RestServicesProcessor.class);

    @Override
    public void process(ProcessingContext ctx) {
        try {
            var generator = new EndpointHandlerGenerator(ctx);
            for (var e : ctx.roundEnv().getElementsAnnotatedWith(Endpoint.class)) {
                var methodElement = (ExecutableElement) e;
                generator.generate(methodElement);
            }
        } catch (IOException | TemplateException e) {
            log.error("Error processing rest services", e);
            ctx.processingEnv().getMessager().printMessage(Diagnostic.Kind.ERROR, "Error processing rest service");
        }
    }
}
