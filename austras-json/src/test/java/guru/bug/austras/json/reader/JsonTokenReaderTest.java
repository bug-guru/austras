/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.json.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.io.StringReader;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class JsonTokenReaderTest {

    @Test
    void testNextSimpleString() {
        StringReader reader = new StringReader("\"Hello\"");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertEquals(TokenType.STRING, json.next());
        assertEquals("Hello" , json.getValue());
    }

    @Test
    void testNextSimpleNull() {
        StringReader reader = new StringReader("null");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertEquals(TokenType.NULL, json.next());
        assertEquals("null" , json.getValue());
    }

    @Test
    void testNextSimpleFalse() {
        StringReader reader = new StringReader("false");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertEquals(TokenType.FALSE, json.next());
        assertEquals("false" , json.getValue());
    }

    @Test
    void testNextSimpleTrue() {
        StringReader reader = new StringReader("true");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertEquals(TokenType.TRUE, json.next());
        assertEquals("true" , json.getValue());
    }

    @Test
    void testNextSimpleColon() {
        StringReader reader = new StringReader(":");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertEquals(TokenType.COLON, json.next());
        assertEquals(":" , json.getValue());
    }

    @Test
    void testNextSimpleComma() {
        StringReader reader = new StringReader(",");
        JsonTokenReader json = new JsonTokenReader(reader);
        Assertions.assertEquals(TokenType.COMMA, json.next());
        assertEquals(",", json.getValue());
    }

    @Test
    void testNextSimpleObject() {
        StringReader reader = new StringReader("{}");
        JsonTokenReader json = new JsonTokenReader(reader);

        Assertions.assertEquals(TokenType.BEGIN_OBJECT, json.next());
        assertEquals("{", json.getValue());

        assertEquals(TokenType.END_OBJECT, json.next());
        assertEquals("}" , json.getValue());
    }


    @Test
    void testNextSimpleArray() {
        StringReader reader = new StringReader("[]");
        JsonTokenReader json = new JsonTokenReader(reader);

        assertEquals(TokenType.BEGIN_ARRAY, json.next());
        assertEquals("[" , json.getValue());

        assertEquals(TokenType.END_ARRAY, json.next());
        assertEquals("]" , json.getValue());
    }

    @Test
    void testNextSimplePositiveIntNumber() {
        StringReader reader = new StringReader("12345");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertEquals(TokenType.NUMBER, json.next());
        assertEquals("12345" , json.getValue());
    }

    @Test
    void testNextSimplePositiveByteNumber() {
        StringReader reader = new StringReader("123");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertEquals(TokenType.NUMBER, json.next());
        assertEquals("123" , json.getValue());
    }

    @Test
    void testNextSimpleNegativeIntNumber() {
        StringReader reader = new StringReader("-54321");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertEquals(TokenType.NUMBER, json.next());
        assertEquals("-54321" , json.getValue());
    }

    @Test
    void testNextSimpleExpNumber() {
        StringReader reader = new StringReader("-54321E3 1E5 0.2e1 0.2e+1 0.2e-1");
        JsonTokenReader json = new JsonTokenReader(reader);

        assertEquals(TokenType.NUMBER, json.next());
        assertEquals("-54321E3" , json.getValue());

        assertEquals(TokenType.NUMBER, json.next());
        assertEquals("1E5" , json.getValue());

        assertEquals(TokenType.NUMBER, json.next());
        assertEquals("0.2e1" , json.getValue());

        assertEquals(TokenType.NUMBER, json.next());
        assertEquals("0.2e+1" , json.getValue());

        assertEquals(TokenType.NUMBER, json.next());
        assertEquals("0.2e-1" , json.getValue());
    }

    @Test
    void testNextSimpleArrayOfFracNumbers() {
        StringReader reader = new StringReader("[34.0, 0.333, 12345.6789, -23.32]");
        JsonTokenReader json = new JsonTokenReader(reader);

        assertEquals(TokenType.BEGIN_ARRAY, json.next());

        assertEquals(TokenType.NUMBER, json.next());
        assertEquals("34.0" , json.getValue());

        Assertions.assertEquals(TokenType.COMMA, json.next());

        assertEquals(TokenType.NUMBER, json.next());
        assertEquals("0.333" , json.getValue());

        Assertions.assertEquals(TokenType.COMMA, json.next());

        assertEquals(TokenType.NUMBER, json.next());
        assertEquals("12345.6789" , json.getValue());

        Assertions.assertEquals(TokenType.COMMA, json.next());

        assertEquals(TokenType.NUMBER, json.next());
        assertEquals("-23.32" , json.getValue());

        assertEquals(TokenType.END_ARRAY, json.next());

        assertEquals(TokenType.EOF, json.next());

    }

    @Test
    void testNextWrongNumbers() {
        StringReader reader = new StringReader("-x -0.x 0.4ex");
        JsonTokenReader json = new JsonTokenReader(reader);

        ParsingException ex = assertThrows(ParsingException.class, json::next);
        assertEquals(1, ex.getPosition().getRow());
        assertEquals(2, ex.getPosition().getCol());

        ex = assertThrows(ParsingException.class, json::next);
        assertEquals(1, ex.getPosition().getRow());
        assertEquals(7, ex.getPosition().getCol());

        ex = assertThrows(ParsingException.class, json::next);
        assertEquals(1, ex.getPosition().getRow());
        assertEquals(13, ex.getPosition().getCol());
    }

    @Test
    void testNextWrongNull() {
        StringReader reader = new StringReader("nUll");
        JsonTokenReader json = new JsonTokenReader(reader);

        ParsingException ex = assertThrows(ParsingException.class, json::next);
        assertEquals(1, ex.getPosition().getRow());
        assertEquals(2, ex.getPosition().getCol());
    }

    @Test
    void testNextUnknownToken() {
        StringReader reader = new StringReader("x");
        JsonTokenReader json = new JsonTokenReader(reader);

        ParsingException ex = assertThrows(ParsingException.class, json::next);
        assertEquals(1, ex.getPosition().getRow());
        assertEquals(1, ex.getPosition().getCol());
    }

    @Test
    void testEOF() {
        StringReader reader = new StringReader("");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertEquals(TokenType.EOF, json.next());
    }

    @Test
    void testSkipWhitespaces() {
        StringReader reader = new StringReader("   \n\n\r\n\t  ");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertEquals(TokenType.EOF, json.next());
    }

    @Test
    void testNextIllegalString() {
        StringReader reader = new StringReader("  \"\n  ");
        JsonTokenReader json = new JsonTokenReader(reader);

        ParsingException ex = assertThrows(ParsingException.class, json::next);
        assertEquals(1, ex.getPosition().getRow());
        assertEquals(4, ex.getPosition().getCol());
    }

    @Test
    void testNextStringEscapes() {
        StringReader reader = new StringReader("\"ab=\\\"c\\\" \\\\ \\/\\b\\n\\r\\t\\u0020\\u0048\"");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertEquals(TokenType.STRING, json.next());
        assertEquals("ab=\"c\" \\ /\b\n\r\t H" , json.getValue());
    }

    @Test
    void testNextStringUnicodeEscapes() {
        StringReader reader = new StringReader("\"\\uD800\\uDF48\\uD83D\\uDE02\\uD841\\uDC57\"");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertEquals(TokenType.STRING, json.next());
        assertEquals("\uD800\uDF48\uD83D\uDE02\uD841\uDC57" , json.getValue());
    }

    @Test
    void testNextStringIllegalEscapes() {
        StringReader reader = new StringReader("\"\\w\"");
        JsonTokenReader json = new JsonTokenReader(reader);

        ParsingException ex = assertThrows(ParsingException.class, json::next);
        assertEquals(1, ex.getPosition().getRow());
        assertEquals(3, ex.getPosition().getCol());
    }

    @Test
    void testNextStringIllegalUnicode() {
        StringReader reader = new StringReader("\"\\u01xx\"");
        JsonTokenReader json = new JsonTokenReader(reader);

        ParsingException ex = assertThrows(ParsingException.class, json::next);
        assertEquals(1, ex.getPosition().getRow());
        assertEquals(6, ex.getPosition().getCol());
    }

    @Test
    void testNextTokenExpectedOneParam() {
        StringReader reader = new StringReader("{");
        JsonTokenReader json = new JsonTokenReader(reader);
        Assertions.assertDoesNotThrow(() -> json.next(TokenType.BEGIN_OBJECT));
    }

    @Test
    void testNextTokenExpectedOneParamNull() {
        StringReader reader = new StringReader("{");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertThrows(NullPointerException.class, () -> json.next((TokenType) null));
    }

    @Test
    void testNextTokenUnexpectedOneParam() {
        StringReader reader = new StringReader("{");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertThrows(ParsingException.class, () -> json.next(TokenType.END_OBJECT));
    }

    @Test
    void testNextTokenExpectedTwoParam() {
        StringReader reader = new StringReader("{}");
        JsonTokenReader json = new JsonTokenReader(reader);
        ThrowingSupplier<TokenType> tokenTypeSupplier = () -> json.next(TokenType.BEGIN_OBJECT, TokenType.END_OBJECT);
        assertDoesNotThrow(tokenTypeSupplier);
        assertDoesNotThrow(tokenTypeSupplier);
    }

    @Test
    void testNextTokenExpectedTwoParamNull() {
        StringReader reader = new StringReader("{");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertThrows(NullPointerException.class, () -> json.next(null, TokenType.BEGIN_ARRAY));
        assertThrows(NullPointerException.class, () -> json.next(TokenType.BEGIN_OBJECT, null));
        assertThrows(NullPointerException.class, () -> json.next(null, null));
    }

    @Test
    void testNextTokenUnexpectedTwoParam() {
        StringReader reader = new StringReader("[");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertThrows(ParsingException.class, () -> json.next(TokenType.END_ARRAY, TokenType.BEGIN_OBJECT));
    }

    @Test
    void testNextTokenExpectedBoolean() {
        Assertions.assertEquals(TokenType.TRUE, new JsonTokenReader(new StringReader("true")).nextNullableBoolean());
        Assertions.assertEquals(TokenType.FALSE, new JsonTokenReader(new StringReader("false")).nextNullableBoolean());
        Assertions.assertEquals(TokenType.NULL, new JsonTokenReader(new StringReader("null")).nextNullableBoolean());
    }

    @Test
    void testNextTokenUnexpectedBoolean() {
        assertThrows(ParsingException.class, () -> new JsonTokenReader(new StringReader("{}")).nextNullableBoolean());
    }

    @Test
    void testNextTokenExpectedSetParam() {
        StringReader reader = new StringReader("{}[]");
        JsonTokenReader json = new JsonTokenReader(reader);
        ThrowingSupplier<TokenType> tokenTypeSupplier = () -> json.next(EnumSet.of(TokenType.BEGIN_ARRAY, TokenType.END_ARRAY, TokenType.BEGIN_OBJECT, TokenType.END_OBJECT));
        assertDoesNotThrow(tokenTypeSupplier);
        assertDoesNotThrow(tokenTypeSupplier);
    }

    @Test
    void testNextTokenExpectedSetParamNull() {
        StringReader reader = new StringReader("{");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertThrows(NullPointerException.class, () -> json.next((EnumSet<TokenType>) null));
    }

    @Test
    void testNextTokenUnexpectedSetParam() {
        StringReader reader = new StringReader("[");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertThrows(ParsingException.class, () -> json.next(EnumSet.of(TokenType.END_ARRAY, TokenType.BEGIN_OBJECT, TokenType.FALSE)));
    }

    @Test
    void testRewind() {
        StringReader reader = new StringReader("[]");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertEquals(TokenType.BEGIN_ARRAY, json.next());
        assertEquals(TokenType.BEGIN_ARRAY, json.getType());
        assertEquals(0, json.getIndex());
        assertDoesNotThrow(json::rewind);
        assertEquals(TokenType.BEGIN_ARRAY, json.next());
        assertEquals(TokenType.BEGIN_ARRAY, json.getType());
        assertEquals(0, json.getIndex());
        assertEquals(TokenType.END_ARRAY, json.next());
        assertEquals(TokenType.END_ARRAY, json.getType());
        assertEquals(1, json.getIndex());
    }

    @Test
    void testRewindTwice() {
        StringReader reader = new StringReader("[]");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertEquals(TokenType.BEGIN_ARRAY, json.next());
        assertEquals(TokenType.BEGIN_ARRAY, json.getType());
        assertEquals(0, json.getIndex());
        assertDoesNotThrow(json::rewind);
        assertThrows(IllegalStateException.class, json::rewind);
    }

    @Test
    void testLevel() {
        StringReader reader = new StringReader("{\"obj\": {\"a\": 1, \"b\": 2, \"c\": {\"x\": 3, \"y\": 4, \"z\": [{}, {}]}}}");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertEquals(0, json.getLevel());
        assertEquals(-1, json.getIndex());

        // 0   0: {
        Assertions.assertEquals(TokenType.BEGIN_OBJECT, json.next());
        Assertions.assertEquals(TokenType.BEGIN_OBJECT, json.getType());
        assertEquals("{", json.getValue());
        assertEquals(1, json.getLevel());
        assertEquals(0, json.getIndex());

        // 1   1: "obj"
        Assertions.assertEquals(TokenType.STRING, json.next());
        Assertions.assertEquals(TokenType.STRING, json.getType());
        assertEquals("obj", json.getValue());
        assertEquals(1, json.getLevel());
        assertEquals(1, json.getIndex());

        // 1   2: :
        Assertions.assertEquals(TokenType.COLON, json.next());
        Assertions.assertEquals(TokenType.COLON, json.getType());
        assertEquals(":", json.getValue());
        assertEquals(1, json.getLevel());
        assertEquals(2, json.getIndex());

        // 2   3: {
        Assertions.assertEquals(TokenType.BEGIN_OBJECT, json.next());
        Assertions.assertEquals(TokenType.BEGIN_OBJECT, json.getType());
        assertEquals("{", json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(3, json.getIndex());

        // 2   4: "a"
        Assertions.assertEquals(TokenType.STRING, json.next());
        Assertions.assertEquals(TokenType.STRING, json.getType());
        assertEquals("a", json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(4, json.getIndex());

        // 2   5: :
        Assertions.assertEquals(TokenType.COLON, json.next());
        Assertions.assertEquals(TokenType.COLON, json.getType());
        assertEquals(":", json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(5, json.getIndex());

        // 2   6: 1
        Assertions.assertEquals(TokenType.NUMBER, json.next());
        Assertions.assertEquals(TokenType.NUMBER, json.getType());
        assertEquals("1", json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(6, json.getIndex());

        // 2   7: ,
        Assertions.assertEquals(TokenType.COMMA, json.next());
        Assertions.assertEquals(TokenType.COMMA, json.getType());
        assertEquals(",", json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(7, json.getIndex());

        // 2   8: "b"
        Assertions.assertEquals(TokenType.STRING, json.next());
        Assertions.assertEquals(TokenType.STRING, json.getType());
        assertEquals("b", json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(8, json.getIndex());

        // 2   9: :
        Assertions.assertEquals(TokenType.COLON, json.next());
        Assertions.assertEquals(TokenType.COLON, json.getType());
        assertEquals(":", json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(9, json.getIndex());

        // 2  10: 2
        Assertions.assertEquals(TokenType.NUMBER, json.next());
        Assertions.assertEquals(TokenType.NUMBER, json.getType());
        assertEquals("2", json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(10, json.getIndex());

        // 2  11: ,
        Assertions.assertEquals(TokenType.COMMA, json.next());
        Assertions.assertEquals(TokenType.COMMA, json.getType());
        assertEquals(",", json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(11, json.getIndex());

        // 2  12: "c"
        Assertions.assertEquals(TokenType.STRING, json.next());
        Assertions.assertEquals(TokenType.STRING, json.getType());
        assertEquals("c", json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(12, json.getIndex());

        // 2  13: :
        Assertions.assertEquals(TokenType.COLON, json.next());
        Assertions.assertEquals(TokenType.COLON, json.getType());
        assertEquals(":", json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(13, json.getIndex());

        // 3  14: {
        Assertions.assertEquals(TokenType.BEGIN_OBJECT, json.next());
        Assertions.assertEquals(TokenType.BEGIN_OBJECT, json.getType());
        assertEquals("{", json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(14, json.getIndex());

        // 3  15: "x"
        Assertions.assertEquals(TokenType.STRING, json.next());
        Assertions.assertEquals(TokenType.STRING, json.getType());
        assertEquals("x", json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(15, json.getIndex());

        // 3  16: :
        Assertions.assertEquals(TokenType.COLON, json.next());
        Assertions.assertEquals(TokenType.COLON, json.getType());
        assertEquals(":", json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(16, json.getIndex());

        // 3  17: 3
        Assertions.assertEquals(TokenType.NUMBER, json.next());
        Assertions.assertEquals(TokenType.NUMBER, json.getType());
        assertEquals("3", json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(17, json.getIndex());

        // 3  18: ,
        Assertions.assertEquals(TokenType.COMMA, json.next());
        Assertions.assertEquals(TokenType.COMMA, json.getType());
        assertEquals(",", json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(18, json.getIndex());

        // 3  19: "y"
        Assertions.assertEquals(TokenType.STRING, json.next());
        Assertions.assertEquals(TokenType.STRING, json.getType());
        assertEquals("y", json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(19, json.getIndex());

        // 3  20: :
        Assertions.assertEquals(TokenType.COLON, json.next());
        Assertions.assertEquals(TokenType.COLON, json.getType());
        assertEquals(":", json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(20, json.getIndex());

        // 3  21: 4
        Assertions.assertEquals(TokenType.NUMBER, json.next());
        Assertions.assertEquals(TokenType.NUMBER, json.getType());
        assertEquals("4", json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(21, json.getIndex());

        // 3  22: ,
        Assertions.assertEquals(TokenType.COMMA, json.next());
        Assertions.assertEquals(TokenType.COMMA, json.getType());
        assertEquals(",", json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(22, json.getIndex());

        // 3  23: "z"
        Assertions.assertEquals(TokenType.STRING, json.next());
        Assertions.assertEquals(TokenType.STRING, json.getType());
        assertEquals("z", json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(23, json.getIndex());

        // 3  24: :
        Assertions.assertEquals(TokenType.COLON, json.next());
        Assertions.assertEquals(TokenType.COLON, json.getType());
        assertEquals(":", json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(24, json.getIndex());

        // 4  25: [
        Assertions.assertEquals(TokenType.BEGIN_ARRAY, json.next());
        Assertions.assertEquals(TokenType.BEGIN_ARRAY, json.getType());
        assertEquals("[", json.getValue());
        assertEquals(4, json.getLevel());
        assertEquals(25, json.getIndex());

        // 5  26: {
        Assertions.assertEquals(TokenType.BEGIN_OBJECT, json.next());
        Assertions.assertEquals(TokenType.BEGIN_OBJECT, json.getType());
        assertEquals("{", json.getValue());
        assertEquals(5, json.getLevel());
        assertEquals(26, json.getIndex());

        // 4  27: }
        Assertions.assertEquals(TokenType.END_OBJECT, json.next());
        Assertions.assertEquals(TokenType.END_OBJECT, json.getType());
        assertEquals("}", json.getValue());
        assertEquals(4, json.getLevel());
        assertEquals(27, json.getIndex());

        // 4  28: ,
        Assertions.assertEquals(TokenType.COMMA, json.next());
        Assertions.assertEquals(TokenType.COMMA, json.getType());
        assertEquals(",", json.getValue());
        assertEquals(4, json.getLevel());
        assertEquals(28, json.getIndex());

        // 5  29: {
        Assertions.assertEquals(TokenType.BEGIN_OBJECT, json.next());
        Assertions.assertEquals(TokenType.BEGIN_OBJECT, json.getType());
        assertEquals("{", json.getValue());
        assertEquals(5, json.getLevel());
        assertEquals(29, json.getIndex());

        // 4  30: }
        Assertions.assertEquals(TokenType.END_OBJECT, json.next());
        Assertions.assertEquals(TokenType.END_OBJECT, json.getType());
        assertEquals("}", json.getValue());
        assertEquals(4, json.getLevel());
        assertEquals(30, json.getIndex());

        // 3  31: ]
        Assertions.assertEquals(TokenType.END_ARRAY, json.next());
        Assertions.assertEquals(TokenType.END_ARRAY, json.getType());
        assertEquals("]", json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(31, json.getIndex());

        // 2  32: }
        Assertions.assertEquals(TokenType.END_OBJECT, json.next());
        Assertions.assertEquals(TokenType.END_OBJECT, json.getType());
        assertEquals("}", json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(32, json.getIndex());

        // 1  33: }
        Assertions.assertEquals(TokenType.END_OBJECT, json.next());
        Assertions.assertEquals(TokenType.END_OBJECT, json.getType());
        assertEquals("}", json.getValue());
        assertEquals(1, json.getLevel());
        assertEquals(33, json.getIndex());

        // 0  34: }
        Assertions.assertEquals(TokenType.END_OBJECT, json.next());
        Assertions.assertEquals(TokenType.END_OBJECT, json.getType());
        assertEquals("}", json.getValue());
        assertEquals(0, json.getLevel());
        assertEquals(34, json.getIndex());

        // EOF
        Assertions.assertEquals(TokenType.EOF, json.next());
    }

}