package guru.bug.austras.codetempl.parser;

class Token {
    enum Type {TXT, CMD, EXP}

    final String value;
    final Type type;

    Token(String value, Type type) {
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
