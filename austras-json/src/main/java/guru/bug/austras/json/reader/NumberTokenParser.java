/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.reader;

class NumberTokenParser {
    private static final String NUMBER_EXPECTED = "Number expected";
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
            throw reader.createParsingException(NUMBER_EXPECTED);
        }
        return valueBuilder.toString();
    }

    private void readOptionalDigitsOrFracOrExp() {
        char ch = readOptionalDigits();
        readOptionalFracOrExp(ch);
    }

    private char readOptionalDigits() {
        char ch = reader.next();
        while (ch >= '0' && ch <= '9') {
            valueBuilder.append(ch);
            ch = reader.next();
        }
        return ch;
    }

    private void readOptionalFracOrExp(char ch) {
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

    private void readDigitsAndOptionalExp() {
        char ch = reader.next();
        if (ch >= '0' && ch <= '9') {
            valueBuilder.append(ch);
            ch = readOptionalDigits();
        } else {
            throw reader.createParsingException(NUMBER_EXPECTED);
        }
        if (ch == 'e' || ch == 'E') {
            valueBuilder.append(ch);
            readExp();
        } else {
            reader.back();
        }
    }

    private void readExp() {
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
            throw reader.createParsingException(NUMBER_EXPECTED);
        }
    }

}
