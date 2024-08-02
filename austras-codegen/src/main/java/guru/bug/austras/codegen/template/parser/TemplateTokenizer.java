/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template.parser;

import java.util.List;

public class TemplateTokenizer extends Tokenizer<TemplateToken> {
    public TemplateTokenizer() {
        super(List.of(
                new NewLineTokenProcessor(),
                new ValueInsertionTokenProcessor(),
                new BlockInsertionTokenProcessor(),
                new TextTokenProcessor()));
    }
}
