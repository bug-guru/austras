/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.codegen.template.parser;

public class NewLineTokenProcessor implements TokenProcessor<TemplateToken> {
    private State state = State.READY;

    @Override
    public ProcessResult process(int codePoint) {
        if (state == State.READY) {
            if (codePoint == '\r') {
                state = State.FIRST_CR;
                return ProcessResult.ACCEPT_FORCE_NEXT;
            } else if (codePoint == '\n') {
                return ProcessResult.COMPLETE;
            } else {
                return ProcessResult.REJECT;
            }
        } else if (state == State.FIRST_CR) {
            if (codePoint == '\n') {
                return ProcessResult.COMPLETE;
            } else {
                return ProcessResult.COMPLETE_REWIND;
            }
        }
        throw new IllegalStateException();
    }

    @Override
    public TemplateToken complete() {
        return new TemplateToken(TemplateToken.Type.NEW_LINE, "\n");
    }

    @Override
    public void reset() {
        state = State.READY;
    }

    private enum State {READY, FIRST_CR}
}
