package guru.bug.austras.convert.engine.json.reader;

public enum TokenType {
    BEGIN_OBJECT,
    END_OBJECT,
    BEGIN_ARRAY,
    END_ARRAY,
    STRING,
    COLON,
    COMMA,
    NUMBER,
    TRUE,
    FALSE,
    NULL,
    EOF
}
