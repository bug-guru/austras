package guru.bug.austras.codetempl.parser.tokenizer;

import java.util.function.Function;

public class StringLiteralTokenProcessor<T> implements TokenProcessor<T> {
    private final StringBuilder literal = new StringBuilder();
    private State state = State.INITIAL;
    private boolean escaped;
    private final Function<String, T> converter;

    protected StringLiteralTokenProcessor(Function<String, T> converter) {
        this.converter = converter;
    }

    @Override
    public ProcessResult process(int codePoint) {
        switch (state) {
            case INITIAL:
                return processStart(codePoint);
            case STARTED:
                return processLiteral(codePoint);
            default:
                throw new IllegalStateException();
        }
    }

    private ProcessResult processStart(int codePoint) {
        if (codePoint == '"') {
            state = State.STARTED;
            return ProcessResult.ACCEPT_FORCE_NEXT;
        } else {
            return ProcessResult.REJECT;
        }
    }

    private ProcessResult processLiteral(int codePoint) {
        if (!escaped && codePoint == '\\') {
            escaped = true;
            return ProcessResult.ACCEPT_FORCE_NEXT;
        }
        if (!escaped && codePoint == '"') {
            state = State.COMPLETED;
            return ProcessResult.COMPLETE;
        }
        literal.appendCodePoint(codePoint);
        return ProcessResult.ACCEPT_FORCE_NEXT;
    }

    @Override
    public T complete() {
        var result = literal.toString();
        var tmpState = state;
        literal.setLength(0);
        state = State.INITIAL;
        escaped = false;
        if (tmpState == State.COMPLETED) {
            return converter.apply(result);
        } else {
            throw new IllegalStateException("Not completed");
        }
    }

    private enum State {INITIAL, STARTED, COMPLETED}
}
