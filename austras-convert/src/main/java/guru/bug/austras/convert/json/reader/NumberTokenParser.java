package guru.bug.austras.convert.json.reader;

class NumberTokenParser {
    private final JsonBufferedReader reader;
    private final StringBuilder valueBuilder;

    NumberTokenParser(JsonBufferedReader reader, StringBuilder valueBuilder) {
        this.reader = reader;
        this.valueBuilder = valueBuilder;
    }

    String continueReadNumber(char firstCh) {
        char ch = firstCh;
        valueBuilder.setLength(0);
        if (ch == '-') {
            valueBuilder.append(ch);
            ch = reader.next();
        }
        if (ch == '0') {
            valueBuilder.append(ch);
            readOptionalFracOrExp(reader.next());
        } else if (ch >= '1' && ch <= '9') {
            valueBuilder.append(ch);
            readOptionalDigitsOrFracOrExp();
        } else {
            throw reader.createParsingException("Number expected");
        }
        return valueBuilder.toString();
    }

    void readOptionalDigitsOrFracOrExp() {
        char ch = readOptionalDigits();
        readOptionalFracOrExp(ch);
    }

    char readOptionalDigits() {
        char ch = reader.next();
        while (ch >= '0' && ch <= '9') {
            valueBuilder.append(ch);
            ch = reader.next();
        }
        return ch;
    }

    void readOptionalFracOrExp(char ch) {
        if (ch == '.') {
            valueBuilder.append(ch);
            readDigitsAndOptionalExp();
        } else if (ch == 'e' || ch == 'E') {
            valueBuilder.append(ch);
            readExp();
        } else {
            reader.back();
        }
    }

    void readDigitsAndOptionalExp() {
        char ch = reader.next();
        if (ch >= '0' && ch <= '9') {
            valueBuilder.append(ch);
            ch = readOptionalDigits();
        } else {
            throw reader.createParsingException("Number expected");
        }
        if (ch == 'e' || ch == 'E') {
            valueBuilder.append(ch);
            readExp();
        } else {
            reader.back();
        }
    }

    void readExp() {
        char ch = reader.next();
        if (ch == '+' || ch == '-') {
            valueBuilder.append(ch);
            ch = reader.next();
        }
        if (ch >= '0' && ch <= '9') {
            valueBuilder.append(ch);
            readOptionalDigits();
            reader.back();
        } else {
            throw reader.createParsingException("Number expected");
        }
    }

}
