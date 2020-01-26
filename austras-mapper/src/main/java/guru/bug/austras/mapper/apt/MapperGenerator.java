/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.apt;

import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.codegen.BodyProcessor;
import guru.bug.austras.codegen.JavaFileGenerator;
import guru.bug.austras.codegen.Template;

@Template(file = "Mapper.java.txt")
public class MapperGenerator extends JavaFileGenerator {

    private final ProcessingContext ctx;

    protected MapperGenerator(ProcessingContext ctx) {
        this.ctx = ctx;
    }

    public void generate(MapperModel model) {
        generate(ctx.processingEnv().getFiler());
    }

    @Override
    public String getPackageName() {
        return null;
    }

    @Override
    public String getSimpleClassName() {
        return null;
    }

    @Template(name = "GENERATE_TARGET_INSTANCE", value = "$WITH_SETTERS$$WITH_BUILDER$$WITH_CONSTRUCTOR$")
    public void generateTargetInstance(BodyProcessor body) {
        body.process();
    }

    @Template(name = "WITH_SETTERS",
            value = "        var result = new $TARGET_TYPE$();\n" +
                    "        #PROPERTIES#\n" +
                    "        result.$PROPERTY_TARGET_SETTER$(#CONVERTER#source.$PROPERTY_SOURCE_GETTER$()#END#);\n" +
                    "        #END#\n" +
                    "        return result;\n")
    public void generateJavaBeanInstance(BodyProcessor body) {
        body.process();
    }

    @Template(name = "WITH_BUILDER",
            value = "        return $TARGET_TYPE$.builder()\n" +
                    "        #PROPERTIES#\n" +
                    "            .$PROPERTY_TARGET_BUILDER_SETTER$(#CONVERTER#source.$PROPERTY_SOURCE_GETTER$()#END#)\n" +
                    "        #END#\n" +
                    "            .build();\n")
    public void generateInstanceWithBuilder(BodyProcessor body) {
        body.process();
    }

    @Template(name = "WITH_CONSTRUCTOR",
            value = "        return new $TARGET_TYPE$(\n" +
                    "        #PROPERTIES#\n" +
                    "            #CONVERTER#source.$PROPERTY_SOURCE_GETTER$()#END#$,$\n" +
                    "        #END#\n" +
                    "        );\n")
    public void generateInstanceWithConstructor(BodyProcessor body) {
        body.process();
    }
}
