package guru.bug.austras.convert.json.reader;

import java.io.Reader;
import java.util.EnumSet;

import static java.util.Objects.requireNonNull;

public class JsonTokenReader {
    private static final char[] NULL = {'u', 'l', 'l'};
    private static final char[] FALSE = {'a', 'l', 's', 'e'};
    private static final char[] TRUE = {'r', 'u', 'e'};
    private final JsonBufferedReader reader;
    private final StringTokenParser stringTokenParser;
    private final NumberTokenParser numberTokenParser;
    private boolean rewind = false;
    private TokenType type;
    private String value;
    private int index = -1;
    private int level;

    public JsonTokenReader(Reader reader) {
        requireNonNull(reader);
        this.reader = new JsonBufferedReader(reader);
        StringBuilder valueBuilder = new StringBuilder();
        this.stringTokenParser = new StringTokenParser(this.reader, valueBuilder);
        this.numberTokenParser = new NumberTokenParser(this.reader, valueBuilder);
    }

    TokenType next(TokenType expected) {
        requireNonNull(expected);
        var tokenType = next();
        if (expected == tokenType) {
            return tokenType;
        } else {
            throw createParsingException("Unexpected token " + tokenType + ". Expected tokens: [" + expected + "]");
        }
    }

    TokenType next(TokenType expected1, TokenType expected2) {
        requireNonNull(expected1);
        requireNonNull(expected2);
        var tokenType = next();
        if (expected1 == tokenType || expected2 == tokenType) {
            return tokenType;
        } else {
            throw createParsingException("Unexpected token " + tokenType + ". Expected tokens: [" + expected1 + ", " + expected2 + "]");
        }
    }

    TokenType next(TokenType expected1, TokenType expected2, TokenType expected3) {
        requireNonNull(expected1);
        requireNonNull(expected2);
        requireNonNull(expected3);
        var tokenType = next();
        if (expected1 == tokenType || expected2 == tokenType || expected3 == tokenType) {
            return tokenType;
        } else {
            throw createParsingException("Unexpected token " + tokenType + ". Expected tokens: [" + expected1 + ", " + expected2 + "]");
        }
    }

    TokenType next(EnumSet<TokenType> expected) {
        requireNonNull(expected);
        var tokenType = next();
        if (expected.contains(tokenType)) {
            return tokenType;
        } else {
            throw createParsingException("Unexpected token " + tokenType + ". Expected tokens: " + expected);
        }
    }

    void rewind() {
        if (rewind) {
            throw new IllegalStateException("Rewind twice");
        }
        rewind = true;
    }

    TokenType next() {
        if (rewind) {
            rewind = false;
            return type;
        }
        char ch = skipWhitespaces();
        switch (ch) {
            case '"':
                value = stringTokenParser.continueReadString();
                index++;
                return type = TokenType.STRING;
            case ':':
                value = ":";
                index++;
                return type = TokenType.COLON;
            case '{':
                value = "{";
                index++;
                level++;
                return type = TokenType.BEGIN_OBJECT;
            case '}':
                value = "}";
                index++;
                level--;
                return type = TokenType.END_OBJECT;
            case '[':
                value = "[";
                index++;
                level++;
                return type = TokenType.BEGIN_ARRAY;
            case ']':
                value = "]";
                index++;
                level--;
                return type = TokenType.END_ARRAY;
            case ',':
                value = ",";
                index++;
                return type = TokenType.COMMA;
            case 'n':
                continueRead(NULL);
                value = "null";
                index++;
                return type = TokenType.NULL;
            case 't':
                continueRead(TRUE);
                value = "true";
                index++;
                return type = TokenType.TRUE;
            case 'f':
                continueRead(FALSE);
                value = "false";
                index++;
                return type = TokenType.FALSE;
            case '-':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                value = numberTokenParser.continueReadNumber(ch);
                index++;
                return type = TokenType.NUMBER;
            case '\u0000':
                value = null;
                index++;
                return type = TokenType.EOF;
            default:
                throw createParsingException();
        }
    }


    void continueRead(char[] expected) {
        for (char c : expected) {
            char actCh = reader.next();
            if (actCh != c) {
                throw createParsingException("Unexpected token");
            }
        }
    }


    char skipWhitespaces() {
        for (; ; ) {
            char ch = reader.next();
            switch (ch) {
                case '\u0009':
                case '\n':
                case '\r':
                case '\u0020':
                    continue;
                default:
                    return ch;
            }
        }
    }

    String getValue() {
        return value;
    }

    public TokenType getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public int getLevel() {
        return level;
    }

    public ParsingException createParsingException(String message) {
        return reader.createParsingException(message);
    }

    public ParsingException createParsingException() {
        return reader.createParsingException();
    }
}


