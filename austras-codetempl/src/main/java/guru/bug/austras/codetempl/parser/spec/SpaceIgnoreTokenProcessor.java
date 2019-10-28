package guru.bug.austras.codetempl.parser.spec;

import guru.bug.austras.codetempl.parser.ProcessResult;
import guru.bug.austras.codetempl.parser.TokenProcessor;

public class SpaceIgnoreTokenProcessor<T> implements TokenProcessor<T> {

    @Override
    public ProcessResult process(int codePoint) {
        if (Character.isWhitespace(codePoint)) {
            return ProcessResult.ACCEPT;
        } else {
            return ProcessResult.REJECT;
        }
    }

    @Override
    public T complete() {
        return null;
    }

    @Override
    public void reset() {

    }
}
