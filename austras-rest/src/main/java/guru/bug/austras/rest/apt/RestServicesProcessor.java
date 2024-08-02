/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.rest.apt;

import guru.bug.austras.apt.core.engine.AustrasProcessorPlugin;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.rest.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.ExecutableElement;

public class RestServicesProcessor implements AustrasProcessorPlugin {
    private static final Logger log = LoggerFactory.getLogger(RestServicesProcessor.class);

    @Override
    public void process(ProcessingContext ctx) {
        var generator = new EndpointHandlerGenerator(ctx);
        for (var e : ctx.roundEnv().getElementsAnnotatedWith(Endpoint.class)) {
            var methodElement = (ExecutableElement) e;
            generator.generate(methodElement);
        }
    }
}
