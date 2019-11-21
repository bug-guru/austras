package guru.bug.austras.codegen;

interface TokenProcessor<T> {
    ProcessResult process(int codePoint);

    T complete();

    void reset();
}
