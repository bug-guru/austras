package guru.bug.austras.codetempl.parser.spec;

import guru.bug.austras.codetempl.parser.ProcessResult;
import guru.bug.austras.codetempl.parser.TokenProcessor;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

public class OneCharTokenProcessor<T> implements TokenProcessor<T> {
    private final Set<Integer> expectedCodePoint = new TreeSet<>();
    private final Function<String, T> converter;
    private String result;

    public OneCharTokenProcessor(Function<String, T> converter, int... expectedCodePoint) {
        for (var i : expectedCodePoint) {
            this.expectedCodePoint.add(i);
        }
        this.converter = converter;
    }

    @Override
    public ProcessResult process(int codePoint) {
        if (expectedCodePoint.contains(codePoint)) {
            result = Character.toString(codePoint);
            return ProcessResult.COMPLETE;
        } else {
            return ProcessResult.REJECT;
        }
    }

    @Override
    public T complete() {
        if (result == null) {
            throw new IllegalStateException("Not completed"); // TODO better error handling
        }
        return converter.apply(result);
    }

    @Override
    public void reset() {
        result = null;
    }
}
