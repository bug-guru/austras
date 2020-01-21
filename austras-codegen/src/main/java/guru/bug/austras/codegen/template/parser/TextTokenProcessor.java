/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template.parser;

public class TextTokenProcessor implements TokenProcessor<TemplateToken> {
    private final StringBuilder text = new StringBuilder();

    @Override
    public ProcessResult process(int codePoint) {
        text.appendCodePoint(codePoint);
        return ProcessResult.ACCEPT;
    }

    @Override
    public TemplateToken complete() {
        return new TemplateToken(TemplateToken.Type.TEXT, text.toString());
    }

    @Override
    public void reset() {
        text.setLength(0);
    }
}
