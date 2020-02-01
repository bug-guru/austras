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
    private FieldMapping currentFieldMapping;

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

    @Template(name = "DEPENDENCY_TYPE")
    public String getDependencyType() {
        return tryImport(currentDep.getType().toString());
    }

    @Template(name = "DEPENDENCY_NAME")
    public String getDependencyName() {
        return currentDep.getName();
    }

    @Template(name = "DEPENDENCY_QUALIFIERS")
    public String getDependencyQualifiers() {
        return "";
    }

    @Template(name = ",")
    public String comma() {
        return commaNeeded ? ", " : "";
    }

    @Template(name = "TARGET_TYPE")
    public String getTargetType() {

        return tryImport(model.getTarget().getBeanType().toString());
    }

    @Template(name = "FIELD_MAPPINGS")
    public void processFieldMappings(BodyProcessor body) {
        var i = model.getMappings().iterator();
        while (i.hasNext()) {
            this.currentFieldMapping = i.next();
            this.commaNeeded = i.hasNext();
            body.process();
        }
    }

    @Template(name = "FIELD_MAPPING_TARGET_SETTER")
    public String getFieldMappingTargetSetter() {
        return currentFieldMapping.getTargetField().getSetter().getSimpleName().toString();
    }

    @Template(name = "FIELD_MAPPING_SOURCE_GETTER")
    public String getFieldMappingSourceGetter() {
        return currentFieldMapping.getSourceField().getGetter().getSimpleName().toString();
    }

    @Template(name = "CONVERTER")
    public void processConverter(BodyProcessor body) {
        var converter = currentFieldMapping.getMapperDep();
        if (converter == null) {
            body.process();
        } else {
            var out = body.getOutput();
            out.print(converter.getName());
            out.print(".map(");
            body.process();
            out.print(")");
        }
    }

    @Template(name = "GENERATE_TARGET_INSTANCE",
            value = "        var result = new $TARGET_TYPE$();\n" +
                    "        #FIELD_MAPPINGS#\n" +
                    "        result.$FIELD_MAPPING_TARGET_SETTER$(#CONVERTER#source.$FIELD_MAPPING_SOURCE_GETTER$()#END#);\n" +
                    "        #END#\n" +
                    "        return result;")
    public void generateJavaBeanInstance(BodyProcessor body) {
        body.process();
    }

    @Template(name = "WITH_BUILDER",
            value = "        return $TARGET_TYPE$.builder()\n" +
                    "        #FIELD_MAPPINGS#\n" +
                    "            .$FIELD_MAPPING_TARGET_BUILDER_SETTER$(#CONVERTER#source.$FIELD_MAPPING_SOURCE_GETTER$()#END#)\n" +
                    "        #END#\n" +
                    "            .build();")
    public void generateInstanceWithBuilder(BodyProcessor body) {
        // not yet supported
    }

    @Template(name = "WITH_CONSTRUCTOR",
            value = "        return new $TARGET_TYPE$(\n" +
                    "        #FIELD_MAPPINGS#\n" +
                    "            #CONVERTER#source.$FIELD_MAPPING_SOURCE_GETTER$()#END#$,$\n" +
                    "        #END#\n" +
                    "        );")
    public void generateInstanceWithConstructor(BodyProcessor body) {
        // not yet supported
    }
}
