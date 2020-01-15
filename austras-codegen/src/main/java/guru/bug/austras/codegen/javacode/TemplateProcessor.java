/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.javacode;

import java.util.function.Function;

public interface TemplateProcessor {

    void call(Template template, Function<String, TemplateProcessor> resolver, JavaCodeWriter out);

}
