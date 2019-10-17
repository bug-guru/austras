package guru.bug.austras.codetempl.parser;

import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

public class Tokenizer<T> {
    private final String content;
    private final IntStream codePoints;

    public Tokenizer(String content) {
        this.content = content;
        this.codePoints = content.codePoints();
    }

    public T next(BiConsumer<Integer, StageChecker>... variants) {
        var list = new LinkedList<BiConsumer<Integer, StageChecker>>();

    }

    public enum ResultType {
        ACCEPT_AND_CONTINUE,
        ACCEPT_AND_COMPLETE,
        REJECT_AND_DISPOSE,
        REJECT_AND_COMPLETE
    }

    public interface StageChecker<T> {
        void accept(int codePoint, ResultConsumer<T> result);
    }

    public interface ResultConsumer<T> {
        void acceptAndContinue();

        void acceptAndComplete(T token);

        void rejectAndDispose();

        void rejectAndComplete(T token);
    }

    public static class CheckResult<T> {
        ResultType type;
        T token;
    }
}
