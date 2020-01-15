/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.apt;

import guru.bug.austras.codegen.Template;

@Template(content = """
        package $PACKAGE_NAME$;

        #IMPORTS#
        org.slf4j.Logger
        org.slf4j.LoggerFactory
        #END#

        $BODY$
        """)
public class JavaFileGenerator {


}
