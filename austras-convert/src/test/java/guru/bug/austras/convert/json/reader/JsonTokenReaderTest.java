package guru.bug.austras.convert.json.reader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.io.StringReader;
import java.util.EnumSet;

import static guru.bug.austras.convert.json.reader.TokenType.*;
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
        assertEquals(COMMA, json.next());
        assertEquals("," , json.getValue());
    }

    @Test
    void testNextSimpleObject() {
        StringReader reader = new StringReader("{}");
        JsonTokenReader json = new JsonTokenReader(reader);

        assertEquals(BEGIN_OBJECT, json.next());
        assertEquals("{" , json.getValue());

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

        assertEquals(COMMA, json.next());

        assertEquals(TokenType.NUMBER, json.next());
        assertEquals("0.333" , json.getValue());

        assertEquals(COMMA, json.next());

        assertEquals(TokenType.NUMBER, json.next());
        assertEquals("12345.6789" , json.getValue());

        assertEquals(COMMA, json.next());

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
        assertDoesNotThrow(() -> json.next(BEGIN_OBJECT));
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
        ThrowingSupplier<TokenType> tokenTypeSupplier = () -> json.next(BEGIN_OBJECT, TokenType.END_OBJECT);
        assertDoesNotThrow(tokenTypeSupplier);
        assertDoesNotThrow(tokenTypeSupplier);
    }

    @Test
    void testNextTokenExpectedTwoParamNull() {
        StringReader reader = new StringReader("{");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertThrows(NullPointerException.class, () -> json.next(null, TokenType.BEGIN_ARRAY));
        assertThrows(NullPointerException.class, () -> json.next(BEGIN_OBJECT, null));
        assertThrows(NullPointerException.class, () -> json.next(null, null));
    }

    @Test
    void testNextTokenUnexpectedTwoParam() {
        StringReader reader = new StringReader("[");
        JsonTokenReader json = new JsonTokenReader(reader);
        assertThrows(ParsingException.class, () -> json.next(TokenType.END_ARRAY, BEGIN_OBJECT));
    }

    @Test
    void testNextTokenExpectedBoolean() {
        assertEquals(TRUE, new JsonTokenReader(new StringReader("true")).nextBoolean());
        assertEquals(FALSE, new JsonTokenReader(new StringReader("false")).nextBoolean());
        assertEquals(NULL, new JsonTokenReader(new StringReader("null")).nextBoolean());
    }

    @Test
    void testNextTokenUnexpectedBoolean() {
        assertThrows(ParsingException.class, () -> new JsonTokenReader(new StringReader("{}")).nextBoolean());
    }

    @Test
    void testNextTokenExpectedSetParam() {
        StringReader reader = new StringReader("{}[]");
        JsonTokenReader json = new JsonTokenReader(reader);
        ThrowingSupplier<TokenType> tokenTypeSupplier = () -> json.next(EnumSet.of(TokenType.BEGIN_ARRAY, TokenType.END_ARRAY, BEGIN_OBJECT, TokenType.END_OBJECT));
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
        assertThrows(ParsingException.class, () -> json.next(EnumSet.of(TokenType.END_ARRAY, BEGIN_OBJECT, TokenType.FALSE)));
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
        assertEquals(BEGIN_OBJECT, json.next());
        assertEquals(BEGIN_OBJECT, json.getType());
        assertEquals("{" , json.getValue());
        assertEquals(1, json.getLevel());
        assertEquals(0, json.getIndex());

        // 1   1: "obj"
        assertEquals(STRING, json.next());
        assertEquals(STRING, json.getType());
        assertEquals("obj" , json.getValue());
        assertEquals(1, json.getLevel());
        assertEquals(1, json.getIndex());

        // 1   2: :
        assertEquals(COLON, json.next());
        assertEquals(COLON, json.getType());
        assertEquals(":" , json.getValue());
        assertEquals(1, json.getLevel());
        assertEquals(2, json.getIndex());

        // 2   3: {
        assertEquals(BEGIN_OBJECT, json.next());
        assertEquals(BEGIN_OBJECT, json.getType());
        assertEquals("{" , json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(3, json.getIndex());

        // 2   4: "a"
        assertEquals(STRING, json.next());
        assertEquals(STRING, json.getType());
        assertEquals("a" , json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(4, json.getIndex());

        // 2   5: :
        assertEquals(COLON, json.next());
        assertEquals(COLON, json.getType());
        assertEquals(":" , json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(5, json.getIndex());

        // 2   6: 1
        assertEquals(NUMBER, json.next());
        assertEquals(NUMBER, json.getType());
        assertEquals("1" , json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(6, json.getIndex());

        // 2   7: ,
        assertEquals(COMMA, json.next());
        assertEquals(COMMA, json.getType());
        assertEquals("," , json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(7, json.getIndex());

        // 2   8: "b"
        assertEquals(STRING, json.next());
        assertEquals(STRING, json.getType());
        assertEquals("b" , json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(8, json.getIndex());

        // 2   9: :
        assertEquals(COLON, json.next());
        assertEquals(COLON, json.getType());
        assertEquals(":" , json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(9, json.getIndex());

        // 2  10: 2
        assertEquals(NUMBER, json.next());
        assertEquals(NUMBER, json.getType());
        assertEquals("2" , json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(10, json.getIndex());

        // 2  11: ,
        assertEquals(COMMA, json.next());
        assertEquals(COMMA, json.getType());
        assertEquals("," , json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(11, json.getIndex());

        // 2  12: "c"
        assertEquals(STRING, json.next());
        assertEquals(STRING, json.getType());
        assertEquals("c" , json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(12, json.getIndex());

        // 2  13: :
        assertEquals(COLON, json.next());
        assertEquals(COLON, json.getType());
        assertEquals(":" , json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(13, json.getIndex());

        // 3  14: {
        assertEquals(BEGIN_OBJECT, json.next());
        assertEquals(BEGIN_OBJECT, json.getType());
        assertEquals("{" , json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(14, json.getIndex());

        // 3  15: "x"
        assertEquals(STRING, json.next());
        assertEquals(STRING, json.getType());
        assertEquals("x" , json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(15, json.getIndex());

        // 3  16: :
        assertEquals(COLON, json.next());
        assertEquals(COLON, json.getType());
        assertEquals(":" , json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(16, json.getIndex());

        // 3  17: 3
        assertEquals(NUMBER, json.next());
        assertEquals(NUMBER, json.getType());
        assertEquals("3" , json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(17, json.getIndex());

        // 3  18: ,
        assertEquals(COMMA, json.next());
        assertEquals(COMMA, json.getType());
        assertEquals("," , json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(18, json.getIndex());

        // 3  19: "y"
        assertEquals(STRING, json.next());
        assertEquals(STRING, json.getType());
        assertEquals("y" , json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(19, json.getIndex());

        // 3  20: :
        assertEquals(COLON, json.next());
        assertEquals(COLON, json.getType());
        assertEquals(":" , json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(20, json.getIndex());

        // 3  21: 4
        assertEquals(NUMBER, json.next());
        assertEquals(NUMBER, json.getType());
        assertEquals("4" , json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(21, json.getIndex());

        // 3  22: ,
        assertEquals(COMMA, json.next());
        assertEquals(COMMA, json.getType());
        assertEquals("," , json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(22, json.getIndex());

        // 3  23: "z"
        assertEquals(STRING, json.next());
        assertEquals(STRING, json.getType());
        assertEquals("z" , json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(23, json.getIndex());

        // 3  24: :
        assertEquals(COLON, json.next());
        assertEquals(COLON, json.getType());
        assertEquals(":" , json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(24, json.getIndex());

        // 4  25: [
        assertEquals(BEGIN_ARRAY, json.next());
        assertEquals(BEGIN_ARRAY, json.getType());
        assertEquals("[" , json.getValue());
        assertEquals(4, json.getLevel());
        assertEquals(25, json.getIndex());

        // 5  26: {
        assertEquals(BEGIN_OBJECT, json.next());
        assertEquals(BEGIN_OBJECT, json.getType());
        assertEquals("{" , json.getValue());
        assertEquals(5, json.getLevel());
        assertEquals(26, json.getIndex());

        // 4  27: }
        assertEquals(END_OBJECT, json.next());
        assertEquals(END_OBJECT, json.getType());
        assertEquals("}" , json.getValue());
        assertEquals(4, json.getLevel());
        assertEquals(27, json.getIndex());

        // 4  28: ,
        assertEquals(COMMA, json.next());
        assertEquals(COMMA, json.getType());
        assertEquals("," , json.getValue());
        assertEquals(4, json.getLevel());
        assertEquals(28, json.getIndex());

        // 5  29: {
        assertEquals(BEGIN_OBJECT, json.next());
        assertEquals(BEGIN_OBJECT, json.getType());
        assertEquals("{" , json.getValue());
        assertEquals(5, json.getLevel());
        assertEquals(29, json.getIndex());

        // 4  30: }
        assertEquals(END_OBJECT, json.next());
        assertEquals(END_OBJECT, json.getType());
        assertEquals("}" , json.getValue());
        assertEquals(4, json.getLevel());
        assertEquals(30, json.getIndex());

        // 3  31: ]
        assertEquals(END_ARRAY, json.next());
        assertEquals(END_ARRAY, json.getType());
        assertEquals("]" , json.getValue());
        assertEquals(3, json.getLevel());
        assertEquals(31, json.getIndex());

        // 2  32: }
        assertEquals(END_OBJECT, json.next());
        assertEquals(END_OBJECT, json.getType());
        assertEquals("}" , json.getValue());
        assertEquals(2, json.getLevel());
        assertEquals(32, json.getIndex());

        // 1  33: }
        assertEquals(END_OBJECT, json.next());
        assertEquals(END_OBJECT, json.getType());
        assertEquals("}" , json.getValue());
        assertEquals(1, json.getLevel());
        assertEquals(33, json.getIndex());

        // 0  34: }
        assertEquals(END_OBJECT, json.next());
        assertEquals(END_OBJECT, json.getType());
        assertEquals("}" , json.getValue());
        assertEquals(0, json.getLevel());
        assertEquals(34, json.getIndex());

        // EOF
        assertEquals(EOF, json.next());
    }

}