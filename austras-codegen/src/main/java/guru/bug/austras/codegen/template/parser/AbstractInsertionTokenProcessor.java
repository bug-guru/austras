/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.codegen.template.parser;

public abstract class AbstractInsertionTokenProcessor implements TokenProcessor<TemplateToken> {
    private final StringBuilder content = new StringBuilder();
    private final int symbol;
    private final TemplateToken.Type type;
    private State state = State.READY;

    public AbstractInsertionTokenProcessor(int symbol, TemplateToken.Type type) {
        this.symbol = symbol;
        this.type = type;
    }

    @Override
    public ProcessResult process(int codePoint) {
        if (state == State.READY) {
            return processStart(codePoint);
        }
        if (state == State.STARTED) {
            return processBody(codePoint);
        }
        return null;
    }

    private ProcessResult processStart(int codePoint) {
        if (codePoint == symbol) {
            state = State.STARTED;
            return ProcessResult.ACCEPT_FORCE_NEXT;
        } else {
            return ProcessResult.REJECT;
        }
    }

    private ProcessResult processBody(int codePoint) {
        if (codePoint == symbol) {
            state = State.COMPLETED;
            return ProcessResult.COMPLETE;
        }
        content.appendCodePoint(codePoint);
        return ProcessResult.ACCEPT_FORCE_NEXT;
    }


    @Override
    public void reset() {
        content.setLength(0);
        state = State.READY;
    }

    @Override
    public TemplateToken complete() {
        if (state != State.COMPLETED) {
            throw new IllegalStateException("Unexpected state " + state);
        }
        String body = content.toString();
        return new TemplateToken(type, body);
    }

    private enum State {READY, STARTED, COMPLETED}
}
