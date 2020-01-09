/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.apt;

import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.codegen.JavaGenerator;
import guru.bug.austras.codegen.Template;
import guru.bug.austras.codegen.TemplateException;

import java.io.IOException;

public class MapperGenerator extends JavaGenerator {

    protected MapperGenerator(ProcessingContext ctx) throws IOException, TemplateException {
        super(ctx.processingEnv().getFiler());
    }

    @Override
    public String getPackageName() {
        return null;
    }

    @Override
    public String getSimpleClassName() {
        return null;
    }

    @Template(name = "GENERATE_TARGET_INSTANCE", content = ".....")
    public String generateTargetInstance() {

    }
}
