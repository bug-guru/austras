package guru.bug.austras.codetempl;

public interface TokenProcessor<T> {
    ProcessResult process(int codePoint);

    T complete();

    void reset();
}
