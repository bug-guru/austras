package guru.bug.austras.codetempl.parser.template;

import guru.bug.austras.codetempl.parser.ProcessResult;
import guru.bug.austras.codetempl.parser.TokenProcessor;

public class TemplateNewLineTokenProcessor implements TokenProcessor<TemplateToken> {
    private State state = State.READY;

    @Override
    public ProcessResult process(int codePoint) {
        switch (state) {
            case READY:
                if (codePoint == '\r') {
                    state = State.FIRST_CR;
                    return ProcessResult.ACCEPT_FORCE_NEXT;
                } else if (codePoint == '\n') {
                    return ProcessResult.COMPLETE;
                } else {
                    return ProcessResult.REJECT;
                }
            case FIRST_CR:
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
