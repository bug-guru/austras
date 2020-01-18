/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template;

public class ValueInsertionTokenProcessor extends AbstractInsertionTokenProcessor {
    ValueInsertionTokenProcessor() {
        super('$', TemplateToken.Type.VALUE);
    }
}
