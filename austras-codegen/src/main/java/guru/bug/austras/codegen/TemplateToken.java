package guru.bug.austras.codegen;

public class TemplateToken {
    public enum Type {TEXT, NEW_LINE, BLOCK, VALUE}

    private final String value;
    private final Type type;

    public TemplateToken(Type type, String value) {
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
