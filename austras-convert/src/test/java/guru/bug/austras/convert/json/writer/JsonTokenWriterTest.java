package guru.bug.austras.convert.json.writer;

import guru.bug.austras.convert.json.utils.JsonWritingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
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
        writer.writeString("Hello!");
        assertEquals("\"Hello!\"" , out.toString());
    }

    @Test
    void testWriteRusString() {
        writer.writeString("По русски");
        assertEquals("\"По русски\"" , out.toString());
    }

    @Test
    void testWriteSymbolsString() {
        writer.writeString("$¢ह€㒨");
        assertEquals("\"$¢ह€㒨\"" , out.toString());
    }

    @Test
    void testWriteEscapeSurrogatesString() {
        writer.writeString("\uD800\uDF48\uD83D\uDE02\uD841\uDC57");
        assertEquals("\"\uD800\uDF48\uD83D\uDE02\uD841\uDC57\"" , out.toString());
    }

    @Test
    void testWriteEscapeSpecString() {
        writer.writeString("a\rb\n\t\\/\b");
        assertEquals("\"a\\rb\\n\\t\\\\/\\b\"" , out.toString());
    }

    @Test
    void testWriteString_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeString("abc\""));
    }

    @Test
    void testWriteMaxByte() {
        writer.writeByte(Byte.MAX_VALUE);
        assertEquals(String.valueOf(Byte.MAX_VALUE), out.toString());
    }

    @Test
    void testWriteMinByte() {
        writer.writeByte(Byte.MIN_VALUE);
        assertEquals(String.valueOf(Byte.MIN_VALUE), out.toString());
    }

    @Test
    void testWriteByte_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeByte((byte) 0));
    }

    @Test
    void testWriteMaxShort() {
        writer.writeShort(Short.MAX_VALUE);
        assertEquals(String.valueOf(Short.MAX_VALUE), out.toString());
    }

    @Test
    void testWriteMinShort() {
        writer.writeShort(Short.MIN_VALUE);
        assertEquals(String.valueOf(Short.MIN_VALUE), out.toString());
    }

    @Test
    void testWriteShort_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeShort((short) 0));
    }

    @Test
    void testWriteMaxInteger() {
        writer.writeInteger(Integer.MAX_VALUE);
        assertEquals(String.valueOf(Integer.MAX_VALUE), out.toString());
    }

    @Test
    void testWriteMinInteger() {
        writer.writeInteger(Integer.MIN_VALUE);
        assertEquals(String.valueOf(Integer.MIN_VALUE), out.toString());
    }

    @Test
    void testWriteInteger_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeInteger(0));
    }

    @Test
    void testWriteMaxLong() {
        writer.writeLong(Long.MAX_VALUE);
        assertEquals(String.valueOf(Long.MAX_VALUE), out.toString());
    }

    @Test
    void testWriteMinLong() {
        writer.writeLong(Long.MIN_VALUE);
        assertEquals(String.valueOf(Long.MIN_VALUE), out.toString());
    }

    @Test
    void testWriteLong_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeLong(0L));
    }

    @Test
    void testWriteMaxDouble() {
        writer.writeDouble(Double.MAX_VALUE);
        assertEquals(String.valueOf(Double.MAX_VALUE), out.toString());
    }

    @Test
    void testWriteMinDouble() {
        writer.writeDouble(Double.MIN_VALUE);
        assertEquals(String.valueOf(Double.MIN_VALUE), out.toString());
    }

    @Test
    void testWriteNaNDouble() {
        writer.writeDouble(Double.NaN);
        assertEquals("null" , out.toString());
    }

    @Test
    void testWritePositiveInfinityDouble() {
        writer.writeDouble(Double.POSITIVE_INFINITY);
        assertEquals("null" , out.toString());
    }

    @Test
    void testWriteNegativeInfinityDouble() {
        writer.writeDouble(Double.NEGATIVE_INFINITY);
        assertEquals("null" , out.toString());
    }

    @Test
    void testWriteDouble_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeDouble(0D));
    }

    @Test
    void testWriteMaxFloat() {
        writer.writeFloat(Float.MAX_VALUE);
        assertEquals(String.valueOf(Float.MAX_VALUE), out.toString());
    }

    @Test
    void testWriteMinFloat() {
        writer.writeFloat(Float.MIN_VALUE);
        assertEquals(String.valueOf(Float.MIN_VALUE), out.toString());
    }

    @Test
    void testWriteNaNFloat() {
        writer.writeFloat(Float.NaN);
        assertEquals("null" , out.toString());
    }

    @Test
    void testWritePositiveInfinityFloat() {
        writer.writeFloat(Float.POSITIVE_INFINITY);
        assertEquals("null" , out.toString());
    }

    @Test
    void testWriteNegativeInfinityFloat() {
        writer.writeFloat(Float.NEGATIVE_INFINITY);
        assertEquals("null" , out.toString());
    }

    @Test
    void testWriteFloat_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeFloat(0F));
    }

    @Test
    void testWriteBigInteger() {
        writer.writeBigInteger(new BigInteger("999999999999999999999999999999999999999999999999999999999999999999999999999"));
        assertEquals("999999999999999999999999999999999999999999999999999999999999999999999999999" , out.toString());
    }

    @Test
    void testWriteBigInteger_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeBigInteger(BigInteger.ZERO));
    }

    @Test
    void testWriteBigInteger_null() {
        writer.writeBigInteger(null);
        assertEquals("null" , out.toString());
    }

    @Test
    void testWriteBigDecimal() {
        writer.writeBigDecimal(new BigDecimal("999999999999999999999999999999999999999999999999999999999999999999999999999E-999"));
        assertEquals("9.99999999999999999999999999999999999999999999999999999999999999999999999999E-925" , out.toString());
    }

    @Test
    void testWriteBigDecimal_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeBigDecimal(BigDecimal.ZERO));
    }

    @Test
    void testWriteBigDecimal_null() {
        writer.writeBigDecimal(null);
        assertEquals("null" , out.toString());
    }


    @Test
    void writeMinCharacter() {
        writer.writeCharacter(Character.MIN_VALUE);
        assertEquals("\"\u0000\"" , out.toString());
    }

    @Test
    void writeMaxCharacter() {
        writer.writeCharacter(Character.MAX_VALUE);
        assertEquals("\"\uFFFF\"" , out.toString());
    }

    @Test
    void writeCharacter_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeCharacter('x'));
    }

    @Test
    void writeBooleanTrue() {
        writer.writeBoolean(true);
        assertEquals("true" , out.toString());
    }

    @Test
    void writeBooleanFalse() {
        writer.writeBoolean(false);
        assertEquals("false" , out.toString());
    }

    @Test
    void writeBoolean_exception() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeBoolean(true));
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


    private static class ThrowingWriter extends Writer {
        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            throw new IOException("Fake write exception");
        }

        @Override
        public void flush() throws IOException {
            throw new IOException("Fake flush exception");
        }

        @Override
        public void close() throws IOException {
            throw new IOException("Fake close exception");
        }
    }
}