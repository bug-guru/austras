/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

class BlockInsertionTokenProcessor extends AbstractInsertionTokenProcessor {
    BlockInsertionTokenProcessor() {
        super('#', TemplateToken.Type.BLOCK);
    }
}
