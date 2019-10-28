package guru.bug.austras.codetempl.parser.spec;

import guru.bug.austras.codetempl.parser.ProcessResult;
import guru.bug.austras.codetempl.parser.TokenProcessor;

import java.util.function.Function;

public class StringLiteralTokenProcessor<T> implements TokenProcessor<T> {
    private final StringBuilder literal = new StringBuilder();
    private State state = State.READY;
    private boolean escaped;
    private final Function<String, T> converter;

    public StringLiteralTokenProcessor(Function<String, T> converter) {
        this.converter = converter;
    }

    @Override
    public ProcessResult process(int codePoint) {
        switch (state) {
            case READY:
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
        if (tmpState == State.COMPLETED) {
            return converter.apply(result);
        } else {
            throw new IllegalStateException("Not completed");
        }
    }

    @Override
    public void reset() {
        literal.setLength(0);
        state = State.READY;
        escaped = false;
    }

    private enum State {READY, STARTED, COMPLETED}
}
