package guru.bug.austras.codetempl.parser.tokenizer;

public class TemplateNewLineTokenProcessor implements TokenProcessor<TemplateToken> {
    private State state = State.READY;

    @Override
    public ProcessResult process(int codePoint) {
        switch (state) {
            case READY:
                if (codePoint == '\r') {
                    state = State.FIRST_CR;
                } else if (codePoint == '\n') {
                    return ProcessResult.COMPLETE;
                } else {
                    return ProcessResult.REJECT;
                }
            case FIRST_CR:
                if (codePoint == '\n') {
                    return ProcessResult.COMPLETE;
                } else {
                    return ProcessResult.COMPLETE_BACK;
                }
        }
        throw new IllegalStateException();
    }

    @Override
    public TemplateToken complete() {
        state = State.READY;
        return new TemplateToken("\n", TemplateToken.Type.NEW_LINE);
    }

    private enum State {READY, FIRST_CR}
}
