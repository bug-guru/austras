package guru.bug.austras.codetempl.parser.tokenizer;

public class TemplateToken {
    public enum Type {TEXT, NEW_LINE, COMMAND, EXPRESSION}

    private final String value;
    private final Type type;

    public TemplateToken(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Token{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}
