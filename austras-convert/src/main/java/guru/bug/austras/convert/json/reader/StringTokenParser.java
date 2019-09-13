package guru.bug.austras.convert.json.reader;

class StringTokenParser {
    private final JsonBufferedReader reader;
    private final StringBuilder valueBuilder;

    StringTokenParser(JsonBufferedReader reader, StringBuilder valueBuilder) {
        this.reader = reader;
        this.valueBuilder = valueBuilder;
    }

    static int convertHexToDec(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'a' && c <= 'f') {
            return c - 'a' + 10;
        }
        if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10;
        }
        throw new IllegalArgumentException("" + c);
    }

    String continueReadString() {
        valueBuilder.setLength(0);
        char ch;
        while ((ch = reader.next()) != '"') {
            if (ch == '\\') {
                ch = readEscape();
                valueBuilder.append(ch);
                continue;
            }
            if (ch >= '\u0020') {
                valueBuilder.append(ch);
                continue;
            }
            throw reader.createParsingException("Illegal string");
        }
        return valueBuilder.toString();
    }

    char readEscape() {
        char ch = reader.next();
        switch (ch) {
            case '"':
            case '\\':
            case '/':
                return ch;
            case 'b':
                return '\b';
            case 'n':
                return '\n';
            case 'r':
                return '\r';
            case 't':
                return '\t';
            case 'u':
                return readUnicode();
            default:
                throw reader.createParsingException("Illegal string");
        }
    }

    char readUnicode() {
        try {
            int result = 0;
            for (int i = 0; i < 4; i++) {
                char ch = reader.next();
                result = (result << 4) | convertHexToDec(ch);
            }
            return (char) result;
        } catch (IllegalArgumentException e) {
            throw reader.createParsingException("unicode expected", e);
        }
    }
}