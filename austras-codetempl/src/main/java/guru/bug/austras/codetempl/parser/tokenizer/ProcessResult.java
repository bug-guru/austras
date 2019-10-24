package guru.bug.austras.codetempl.parser.tokenizer;

public enum ProcessResult {
    REJECT,
    ACCEPT,
    ACCEPT_FORCE_NEXT,
    COMPLETE,
    COMPLETE_REWIND
}
