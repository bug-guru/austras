package guru.bug.austras.codetempl.parser.tokenizer;

public interface TokenProcessor<T> {
    ProcessResult process(int codePoint);

    T complete();

    void reset();
}
