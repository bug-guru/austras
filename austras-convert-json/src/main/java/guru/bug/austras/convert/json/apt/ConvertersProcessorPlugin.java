/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.json.apt;

import guru.bug.austras.apt.core.engine.AustrasProcessorPlugin;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.type.DeclaredType;
import java.util.HashSet;
import java.util.Set;

public class ConvertersProcessorPlugin implements AustrasProcessorPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertersProcessorPlugin.class);
    private final Set<DeclaredType> generated = new HashSet<>();

    @Override
    public void process(ProcessingContext ctx) {
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
    }

}
