/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.apt;

import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.codegen.BodyBlock;
import guru.bug.austras.codegen.JavaGenerator;
import guru.bug.austras.codegen.Template;
import guru.bug.austras.codegen.TemplateException;

import java.io.IOException;
import java.io.PrintWriter;

public class MapperGenerator extends JavaGenerator {

    protected MapperGenerator(ProcessingContext ctx) throws IOException, TemplateException {
        super(ctx.processingEnv().getFiler());
    }

    @Template(content = """
            package $PACKAGE_NAME$;

            #IMPORTS#
            org.slf4j.Logger
            org.slf4j.LoggerFactory
            guru.bug.austras.mapper.Mapper
            #END#

            $QUALIFIERS$
            public class $SIMPLE_CLASS_NAME$ implements Mapper<$SOURCE_TYPE$, $TARGET_TYPE$> {
                public static final Logger LOGGER = LoggerFactory.getLogger($QUALIFIED_CLASS_NAME$.class);

                #DEPENDENCIES#
                private final $DEPENDENCY_TYPE$ $DEPENDENCY_NAME$;
                #END#

                public $SIMPLE_CLASS_NAME$(
                    #DEPENDENCIES#
                    $DEPENDENCY_QUALIFIERS$ $DEPENDENCY_TYPE$ $DEPENDENCY_NAME$$,$
                    #END#
                ) {
                    #DEPENDENCIES#
                    this.$DEPENDENCY_NAME$ = $DEPENDENCY_NAME$;
                    #END#
                }

                @Override
                public $TARGET_TYPE$ map($SOURCE_TYPE$ source) {
                    $GENERATE_TARGET_INSTANCE$
                }

            }
            """)
    public void generate(MapperModel model) {

    }

    @Override
    public String getPackageName() {
        return null;
    }

    @Override
    public String getSimpleClassName() {
        return null;
    }

    @Template(name = "GENERATE_TARGET_INSTANCE")
    public void generateTargetInstance(PrintWriter out, BodyBlock body) {

    }

    @Template(content = """
                    var result = new $TARGET_TYPE$();
                    #PROPERTIES#
                    result.$PROPERTY_TARGET_SETTER$(#CONVERTER#source.$PROPERTY_SOURCE_GETTER$()#END#);
                    #END#
                    return result;
            """)
    public void generateJavaBeanInstance(PrintWriter out, BodyBlock body) {

    }

    @Template(content = """
                    return $TARGET_TYPE$.builder()
                    #PROPERTIES#
                        .$PROPERTY_TARGET_BUILDER_SETTER$(#CONVERTER#source.$PROPERTY_SOURCE_GETTER$()#END#)
                    #END#
                        .build();
            """)
    public void generateInstanceWithBuilder(PrintWriter out, BodyBlock body) {

    }

    @Template(content = """
                    return new $TARGET_TYPE$(
                    #PROPERTIES#
                        #CONVERTER#source.$PROPERTY_SOURCE_GETTER$()#END#$,$
                    #END#
                    );
            """)
    public void generateInstanceWithConstructor(PrintWriter out, BodyBlock body) {

    }
}
