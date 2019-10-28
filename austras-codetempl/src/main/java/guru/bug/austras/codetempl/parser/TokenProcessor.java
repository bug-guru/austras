package guru.bug.austras.codetempl.parser;

public interface TokenProcessor<T> {
    ProcessResult process(int codePoint);

    T complete();

    void reset();
}
