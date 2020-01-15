/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.javacode;

import guru.bug.austras.common.model.ComponentInfo;

import javax.annotation.processing.Filer;
import java.io.IOException;
import java.io.Writer;

public abstract class JavaFileGenerator {
    private static final CodeTemplate ROOT_TEMPLATE = CodeTemplate.compile("""
            package $PACKAGE_NAME$;

            #IMPORTS#
            org.slf4j.Logger
            org.slf4j.LoggerFactory
            #END#

            $QUALIFIERS$
            public class $SIMPLE_CLASS_NAME$ $EXTENDS$ $IMPLEMENTS$ {
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

                $BODY$

            }
            """);
    private final Filer filer;
    private Context context;

    public JavaFileGenerator(Filer filer) {
        this.filer = filer;
    }

    protected void generate(ComponentInfo component) throws IOException {
        context = new Context();
        context.packageName = component.get;
        context.simpleClassName = simpleClassName;
        context.qualifiedName = packageName + '.' + simpleClassName;
        try (var out = filer.createSourceFile(qualifiedName).openWriter()) {
            context.importsManager = new ImportsManager(packageName);
            context.current = new JavaCodeWriterImpl(this::runTemplate, importsManager, Writer.nullWriter());
            context.current.writeTemplate(template);
            context.current = new JavaCodeWriterImpl(this::runTemplate, importsManager, out);
            context.current.writeTemplate(template);
        } finally {
            context = null
        }
    }

    private void runTemplate(String templateName) {

    }


    private static class Context {
        JavaCodeWriter current;
        String simpleClassName;
        String packageName;
        String qualifiedName;

    }
}
