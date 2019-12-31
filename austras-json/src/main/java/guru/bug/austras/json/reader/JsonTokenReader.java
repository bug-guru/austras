/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.reader;

import java.io.Reader;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import static java.util.Objects.requireNonNull;

class JsonTokenReader {
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

    JsonTokenReader(Reader reader) {
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
            throw createUnexpectedTokenException(tokenType, expected);
        }
    }

    TokenType next(TokenType expected1, TokenType expected2) {
        requireNonNull(expected1);
        requireNonNull(expected2);
        var tokenType = next();
        if (expected1 == tokenType || expected2 == tokenType) {
            return tokenType;
        } else {
            throw createUnexpectedTokenException(tokenType, expected1, expected2);
        }
    }

    TokenType nextNullableBoolean() {
        var tokenType = next();
        if (TokenType.TRUE == tokenType || TokenType.FALSE == tokenType || TokenType.NULL == tokenType) {
            return tokenType;
        } else {
            throw createUnexpectedTokenException(tokenType, TokenType.TRUE, TokenType.FALSE, TokenType.NULL);
        }
    }

    TokenType next(EnumSet<TokenType> expected) {
        requireNonNull(expected);
        var tokenType = next();
        if (expected.contains(tokenType)) {
            return tokenType;
        } else {
            throw createUnexpectedTokenException(tokenType, expected);
        }
    }

    void rewind() {
        if (rewind) {
            throw new IllegalStateException("Rewind twice");
        }
        rewind = true;
        index--;
    }

    TokenType next() {
        if (rewind) {
            rewind = false;
            index++;
            return type;
        }
        char ch = skipWhitespaces();
        switch (ch) {
            case '"':
                value = stringTokenParser.continueReadString();
                index++;
                type = TokenType.STRING;
                return type;
            case ':':
                value = ":";
                index++;
                type = TokenType.COLON;
                return type;
            case '{':
                value = "{";
                index++;
                level++;
                type = TokenType.BEGIN_OBJECT;
                return type;
            case '}':
                value = "}";
                index++;
                level--;
                type = TokenType.END_OBJECT;
                return type;
            case '[':
                value = "[";
                index++;
                level++;
                type = TokenType.BEGIN_ARRAY;
                return type;
            case ']':
                value = "]";
                index++;
                level--;
                type = TokenType.END_ARRAY;
                return type;
            case ',':
                value = ",";
                index++;
                type = TokenType.COMMA;
                return type;
            case 'n':
                continueRead(NULL);
                value = "null";
                index++;
                type = TokenType.NULL;
                return type;
            case 't':
                continueRead(TRUE);
                value = "true";
                index++;
                type = TokenType.TRUE;
                return type;
            case 'f':
                continueRead(FALSE);
                value = "false";
                index++;
                type = TokenType.FALSE;
                return type;
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
                type = TokenType.NUMBER;
                return type;
            case '\u0000':
                value = null;
                index++;
                type = TokenType.EOF;
                return type;
            default:
                throw createParsingException();
        }
    }


    private void continueRead(char[] expected) {
        for (char c : expected) {
            char actCh = reader.next();
            if (actCh != c) {
                throw createParsingException("Unexpected token");
            }
        }
    }


    private char skipWhitespaces() {
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

    TokenType getType() {
        return type;
    }

    int getIndex() {
        return index;
    }

    int getLevel() {
        return level;
    }

    private ParsingException createUnexpectedTokenException(TokenType actual, TokenType... expected) {
        return createUnexpectedTokenException(actual, List.of(expected));
    }

    private ParsingException createUnexpectedTokenException(TokenType actual, Collection<TokenType> expected) {
        return createParsingException(String.format("Unexpected token [%s]. Expected tokens: %s" , actual, expected));
    }


    ParsingException createParsingException(String message) {
        return reader.createParsingException(message);
    }

    private ParsingException createParsingException() {
        return reader.createParsingException();
    }
}


