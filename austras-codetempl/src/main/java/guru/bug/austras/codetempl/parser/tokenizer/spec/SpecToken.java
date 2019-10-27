package guru.bug.austras.codetempl.parser.tokenizer.spec;

public class SpecToken {
    private final Type type;
    private final String value;

    public SpecToken(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public enum Type {
        NAME,
        STRING_LITERAL,
        QUESTION_MARK,
        COLON
    }
}
