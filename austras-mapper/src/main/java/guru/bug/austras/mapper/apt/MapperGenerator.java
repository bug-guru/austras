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

import javax.lang.model.element.TypeElement;

@Template(file = "Mapper.java.txt")
public class MapperGenerator extends JavaFileGenerator {
    private final ProcessingContext ctx;
    private String packageName;
    private String simpleClassName;
    private MapperModel model;
    private boolean commaNeeded;
    private FieldMapperDependency currentDep;

    protected MapperGenerator(ProcessingContext ctx) {
        this.ctx = ctx;
    }

    public void generate(MapperModel model) {
        this.model = model;
        var srcTypeElement = (TypeElement) model.getSource().getBeanType().asElement();
        var trgTypeElement = (TypeElement) model.getTarget().getBeanType().asElement();
        packageName = ctx.processingEnv().getElementUtils().getPackageOf(srcTypeElement).getQualifiedName().toString();
        simpleClassName = srcTypeElement.getSimpleName() + "To" + trgTypeElement.getSimpleName() + "Mapper";
        generate(ctx.processingEnv().getFiler());
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String getSimpleClassName() {
        return simpleClassName;
    }

    @Template(name = "QUALIFIERS")
    public String getComponentQualifiers() {
        return model.getQualifiers().toString();
    }

    @Template(name = "SOURCE_TYPE")
    public String getSourceType() {
        return tryImport(model.getSource().getBeanType().toString());
    }

    @Template(name = "DEPENDENCIES")
    public void processDependencies(BodyProcessor body) {
        var i = model.getDependencies().iterator();
        while (i.hasNext()) {
            currentDep = i.next();
            commaNeeded = i.hasNext();
            body.process();
        }
    }

    @Template(name = ",")
    public String comma() {
        return commaNeeded ? ", " : "";
    }

    @Template(name = "TARGET_TYPE")
    public String getTargetType() {

        return tryImport(model.getTarget().getBeanType().toString());
    }

    @Template(name = "GENERATE_TARGET_INSTANCE")
    public void generateTargetInstance(BodyProcessor body) {
        body.process("WITH_SETTERS");
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
        // not yet supported
    }

    @Template(name = "WITH_CONSTRUCTOR",
            value = "        return new $TARGET_TYPE$(\n" +
                    "        #PROPERTIES#\n" +
                    "            #CONVERTER#source.$PROPERTY_SOURCE_GETTER$()#END#$,$\n" +
                    "        #END#\n" +
                    "        );\n")
    public void generateInstanceWithConstructor(BodyProcessor body) {
        // not yet supported
    }
}
