/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.javacode;

import java.io.Writer;
import java.util.function.Function;

public class CodeTemplate implements Template {


    @Override
    public void process(Function<String, TemplateProcessor> resolver, Writer out) {

    }

    public static CodeTemplate compile(String tempate) {

    }
}
