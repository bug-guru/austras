package guru.bug.austras.codetempl.parser.tokenizer.spec;

import guru.bug.austras.codetempl.parser.tokenizer.ProcessResult;
import guru.bug.austras.codetempl.parser.tokenizer.TokenProcessor;

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
