package guru.bug.austras.convert.json.apt;

import guru.bug.austras.apt.core.engine.AustrasProcessorPlugin;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.codegen.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ConvertersProcessorPlugin implements AustrasProcessorPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertersProcessorPlugin.class);
    private final Set<DeclaredType> generated = new HashSet<>();

    @Override
    public void process(ProcessingContext ctx) {
        try {
            var processor = new ConvertersProcessor(ctx);
            var toGenerate = new HashSet<>(processor.process());
            toGenerate.removeAll(generated);

            var contentConverterGenerator = new JsonContentConverterGenerator(ctx);
            for (var ct : toGenerate) {
                if (ct.toString().startsWith("java.")) {
                    continue;
                }
                LOGGER.info("generating json converter for {}", ct);
                contentConverterGenerator.generate(ct);
            }
            generated.addAll(toGenerate);
        } catch (IOException | TemplateException e) {
            // TODO better error handling
            ctx.processingEnv().getMessager().printMessage(Diagnostic.Kind.ERROR, "Error generating content converter");
            throw new IllegalStateException(e);
        }
    }

}
