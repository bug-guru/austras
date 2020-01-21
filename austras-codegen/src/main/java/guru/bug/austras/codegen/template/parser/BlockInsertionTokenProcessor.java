/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template.parser;

public class BlockInsertionTokenProcessor extends AbstractInsertionTokenProcessor {
    public BlockInsertionTokenProcessor() {
        super('#', TemplateToken.Type.BLOCK);
    }
}
