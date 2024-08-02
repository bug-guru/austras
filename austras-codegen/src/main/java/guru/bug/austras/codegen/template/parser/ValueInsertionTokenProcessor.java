/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template.parser;

public class ValueInsertionTokenProcessor extends AbstractInsertionTokenProcessor {
    ValueInsertionTokenProcessor() {
        super('$', TemplateToken.Type.VALUE);
    }
}
