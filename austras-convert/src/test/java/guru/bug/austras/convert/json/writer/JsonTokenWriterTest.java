package guru.bug.austras.convert.json.writer;

import guru.bug.austras.convert.json.utils.JsonWritingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonTokenWriterTest {
    private StringWriter out;
    private JsonTokenWriter writer;
    private JsonTokenWriter throwingWriter;

    @BeforeEach
    void init() {
        out = new StringWriter();
        writer = new JsonTokenWriter(out);
        throwingWriter = new JsonTokenWriter(new ThrowingWriter());
    }

    @Test
    void testCreateWithNullWriter() {
        assertThrows(NullPointerException.class, () -> new JsonTokenWriter(null));
    }

    @Test
    void testWriteRaw() {
        writer.writeRaw("abc\"");
        assertEquals("abc\"" , out.toString());
    }

    @Test
    void testWriteRaw_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeRaw("abc\""));
    }

    @Test
    void testWriteEngString() {
        writer.write("Hello!");
        assertEquals("\"Hello!\"" , out.toString());
    }

    @Test
    void testWriteRusString() {
        writer.write("По русски");
        assertEquals("\"По русски\"" , out.toString());
    }

    @Test
    void testWriteSymbolsString() {
        writer.write("$¢ह€㒨");
        assertEquals("\"$¢ह€㒨\"" , out.toString());
    }

    @Test
    void testWriteEscapeSurrogatesString() {
        writer.write("\uD800\uDF48\uD83D\uDE02\uD841\uDC57");
        assertEquals("\"\uD800\uDF48\uD83D\uDE02\uD841\uDC57\"" , out.toString());
    }

    @Test
    void testWriteEscapeSpecString() {
        writer.write("a\rb\n\t\\/\b");
        assertEquals("\"a\\rb\\n\\t\\\\/\\b\"" , out.toString());
    }

    @Test
    void testWriteString_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.write("abc\""));
    }

    @Test
    void testWriteMaxByte() {
        writer.write(Byte.MAX_VALUE);
        assertEquals(String.valueOf(Byte.MAX_VALUE), out.toString());
    }

    @Test
    void testWriteMinByte() {
        writer.write(Byte.MIN_VALUE);
        assertEquals(String.valueOf(Byte.MIN_VALUE), out.toString());
    }

    @Test
    void testWriteByte_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.write((byte) 0));
    }

    @Test
    void testWriteMaxShort() {
        writer.write(Short.MAX_VALUE);
        assertEquals(String.valueOf(Short.MAX_VALUE), out.toString());
    }

    @Test
    void testWriteMinShort() {
        writer.write(Short.MIN_VALUE);
        assertEquals(String.valueOf(Short.MIN_VALUE), out.toString());
    }

    @Test
    void testWriteShort_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.write((short) 0));
    }

    @Test
    void testWriteMaxInteger() {
        writer.write(Integer.MAX_VALUE);
        assertEquals(String.valueOf(Integer.MAX_VALUE), out.toString());
    }

    @Test
    void testWriteMinInteger() {
        writer.write(Integer.MIN_VALUE);
        assertEquals(String.valueOf(Integer.MIN_VALUE), out.toString());
    }

    @Test
    void testWriteInteger_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.write(0));
    }

    @Test
    void testWriteMaxLong() {
        writer.write(Long.MAX_VALUE);
        assertEquals(String.valueOf(Long.MAX_VALUE), out.toString());
    }

    @Test
    void testWriteMinLong() {
        writer.write(Long.MIN_VALUE);
        assertEquals(String.valueOf(Long.MIN_VALUE), out.toString());
    }

    @Test
    void testWriteLong_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.write(0L));
    }

    @Test
    void testWriteMaxDouble() {
        writer.write(Double.MAX_VALUE);
        assertEquals(String.valueOf(Double.MAX_VALUE), out.toString());
    }

    @Test
    void testWriteMinDouble() {
        writer.write(Double.MIN_VALUE);
        assertEquals(String.valueOf(Double.MIN_VALUE), out.toString());
    }

    @Test
    void testWriteNaNDouble() {
        writer.write(Double.NaN);
        assertEquals("null" , out.toString());
    }

    @Test
    void testWritePositiveInfinityDouble() {
        writer.write(Double.POSITIVE_INFINITY);
        assertEquals("null" , out.toString());
    }

    @Test
    void testWriteNegativeInfinityDouble() {
        writer.write(Double.NEGATIVE_INFINITY);
        assertEquals("null" , out.toString());
    }

    @Test
    void testWriteDouble_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.write(0D));
    }

    @Test
    void testWriteMaxFloat() {
        writer.write(Float.MAX_VALUE);
        assertEquals(String.valueOf(Float.MAX_VALUE), out.toString());
    }

    @Test
    void testWriteMinFloat() {
        writer.write(Float.MIN_VALUE);
        assertEquals(String.valueOf(Float.MIN_VALUE), out.toString());
    }

    @Test
    void testWriteNaNFloat() {
        writer.write(Float.NaN);
        assertEquals("null" , out.toString());
    }

    @Test
    void testWritePositiveInfinityFloat() {
        writer.write(Float.POSITIVE_INFINITY);
        assertEquals("null" , out.toString());
    }

    @Test
    void testWriteNegativeInfinityFloat() {
        writer.write(Float.NEGATIVE_INFINITY);
        assertEquals("null" , out.toString());
    }

    @Test
    void testWriteFloat_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.write(0F));
    }

    @Test
    void testWriteBigInteger() {
        writer.write(new BigInteger("999999999999999999999999999999999999999999999999999999999999999999999999999"));
        assertEquals("999999999999999999999999999999999999999999999999999999999999999999999999999" , out.toString());
    }

    @Test
    void testWriteBigInteger_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.write(BigInteger.ZERO));
    }

    @Test
    void testWriteBigInteger_null() {
        writer.write((BigInteger) null);
        assertEquals("null" , out.toString());
    }

    @Test
    void testWriteBigDecimal() {
        writer.write(new BigDecimal("999999999999999999999999999999999999999999999999999999999999999999999999999E-999"));
        assertEquals("9.99999999999999999999999999999999999999999999999999999999999999999999999999E-925" , out.toString());
    }

    @Test
    void testWriteBigDecimal_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.write(BigDecimal.ZERO));
    }

    @Test
    void testWriteBigDecimal_null() {
        writer.write((BigDecimal) null);
        assertEquals("null" , out.toString());
    }


    @Test
    void writeMinCharacter() {
        writer.write(Character.MIN_VALUE);
        assertEquals("\"\u0000\"" , out.toString());
    }

    @Test
    void writeMaxCharacter() {
        writer.write(Character.MAX_VALUE);
        assertEquals("\"\uFFFF\"" , out.toString());
    }

    @Test
    void writeCharacter_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.write('x'));
    }

    @Test
    void writeBooleanTrue() {
        writer.write(true);
        assertEquals("true" , out.toString());
    }

    @Test
    void writeBooleanFalse() {
        writer.write(false);
        assertEquals("false" , out.toString());
    }

    @Test
    void writeBoolean_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.write(true));
    }

    @Test
    void writeNull() {
        writer.writeNull();
        assertEquals("null" , out.toString());
    }

    @Test
    void writeNull_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeNull());
    }

    @Test
    void writeBeginObject() {
        writer.writeBeginObject();
        assertEquals("{" , out.toString());
    }

    @Test
    void writeBeginObject_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeBeginObject());
    }

    @Test
    void writeEndObject() {
        writer.writeEndObject();
        assertEquals("}" , out.toString());
    }

    @Test
    void writeEndObject_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeEndObject());
    }

    @Test
    void writeBeginArray() {
        writer.writeBeginArray();
        assertEquals("[" , out.toString());
    }

    @Test
    void writeBeginArray_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeBeginArray());
    }

    @Test
    void writeEndArray() {
        writer.writeEndArray();
        assertEquals("]" , out.toString());
    }

    @Test
    void writeEndArray_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeEndArray());
    }

    @Test
    void writeColon() {
        writer.writeColon();
        assertEquals(":" , out.toString());
    }

    @Test
    void writeColon_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeColon());
    }

    @Test
    void writeComma() {
        writer.writeComma();
        assertEquals("," , out.toString());
    }

    @Test
    void writeComma_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeComma());
    }


}