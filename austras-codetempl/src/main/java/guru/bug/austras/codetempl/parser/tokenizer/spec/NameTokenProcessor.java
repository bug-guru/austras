package guru.bug.austras.codetempl.parser.tokenizer.spec;

import guru.bug.austras.codetempl.parser.tokenizer.ProcessResult;
import guru.bug.austras.codetempl.parser.tokenizer.TokenProcessor;

import java.util.function.Function;

public class NameTokenProcessor<T> implements TokenProcessor<T> {
    private final Function<String, T> converter;
    private final StringBuilder result = new StringBuilder();
    private State state = State.READY;

    public NameTokenProcessor(Function<String, T> converter) {
        this.converter = converter;
    }

    @Override
    public ProcessResult process(int codePoint) {
        switch (state) {
            case READY:
                if (Character.isUpperCase(codePoint) && Character.isLetter(codePoint)) {
                    result.appendCodePoint(codePoint);
                    state = State.STARTED;
                    return ProcessResult.ACCEPT_FORCE_NEXT;
                } else {
                    return ProcessResult.REJECT;
                }
            case STARTED:
                if (Character.isUpperCase(codePoint) && Character.isLetterOrDigit(codePoint)
                        || codePoint == '_'
                        || codePoint == '.') {
                    result.appendCodePoint(codePoint);
                    return ProcessResult.ACCEPT_FORCE_NEXT;
                } else {
                    state = State.COMPLETED;
                    return ProcessResult.COMPLETE_REWIND;
                }
            default:
                throw new IllegalStateException(state + " is not supported");
        }
    }

    @Override
    public T complete() {
        if (state != State.COMPLETED) {
            throw new IllegalStateException("Isn't completed"); // TODO better error handling
        }
        return converter.apply(result.toString());
    }

    @Override
    public void reset() {
        state = State.READY;
        result.setLength(0);
    }

    private enum State {READY, STARTED, COMPLETED}
}
