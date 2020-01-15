/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.generator;

import guru.bug.austras.codegen.Template;

@Template(content = """
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
        """)
public class ComponentGenerator extends FileGenerator {


}
