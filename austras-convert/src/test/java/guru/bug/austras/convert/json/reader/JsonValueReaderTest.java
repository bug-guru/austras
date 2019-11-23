package guru.bug.austras.convert.json.reader;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class JsonValueReaderTest {

    private JsonValueReader reader(String json) {
        var strReader = new StringReader(json);
        return JsonValueReader.newInstance(strReader);
    }

    @Test
    void readBoolean() {
        assertTrue(reader("true").readBoolean());
        assertFalse(reader("false").readBoolean());
        assertThrows(ParsingException.class, () -> reader("null").readBoolean());
        assertThrows(ParsingException.class, () -> reader("\"false\"").readBoolean());
    }

    @Test
    void readNullableBoolean() {
        assertTrue(reader("true").readNullableBoolean());
        assertFalse(reader("false").readNullableBoolean());
        assertNull(reader("null").readNullableBoolean());
        assertThrows(ParsingException.class, () -> reader("[").readNullableBoolean());
    }

    @Test
    void readOptionalBoolean() {
        assertTrue(reader("true").readOptionalBoolean().orElseThrow());
        assertFalse(reader("false").readOptionalBoolean().orElseThrow());
        assertTrue(reader("null").readOptionalBoolean().isEmpty());
        assertThrows(ParsingException.class, () -> reader("\"true\"").readOptionalBoolean());
    }

    private <T> void noop(T aBoolean) {

    }

    @Test
    void readBooleanArray() {
        assertEquals(Arrays.asList(true, false, null),
                reader("[true, false, null]").readBooleanArray().orElseThrow().collect(Collectors.toList()));
        assertEquals(0, reader("[]").readBooleanArray().orElseThrow().count());
        assertTrue(reader("null").readBooleanArray().isEmpty());
        assertThrows(ParsingException.class,
                () -> reader("[true,").readBooleanArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class,
                () -> reader("[true, 2]").readBooleanArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class, () -> reader("{true,").readBooleanArray());
    }

    @Test
    void readByte() {
        assertEquals(0, reader("0").readByte());
        assertEquals(-128, reader("-128").readByte());
        assertEquals(127, reader("127").readByte());
        assertThrows(ParsingException.class, () -> reader("null").readByte());
        assertThrows(NumberFormatException.class, () -> reader("128").readByte());
        assertThrows(NumberFormatException.class, () -> reader("-129").readByte());
    }

    @Test
    void readNullableByte() {
        assertEquals((Byte) (byte) 10, reader("10").readNullableByte());
        assertEquals((Byte) (byte) (-128), reader("-128").readNullableByte());
        assertEquals((Byte) (byte) 127, reader("127").readNullableByte());
        assertNull(reader("null").readNullableByte());
        assertThrows(NumberFormatException.class, () -> reader("128").readNullableByte());
        assertThrows(NumberFormatException.class, () -> reader("-129").readNullableByte());
    }

    @Test
    void readOptionalByte() {
        assertEquals((Byte) (byte) -123, reader("-123").readOptionalByte().orElseThrow());
        assertEquals((Byte) (byte) (-128), reader("-128").readOptionalByte().orElseThrow());
        assertEquals((Byte) (byte) 127, reader("127").readOptionalByte().orElseThrow());
        assertTrue(reader("null").readOptionalByte().isEmpty());
        assertThrows(NumberFormatException.class, () -> reader("128").readOptionalByte());
        assertThrows(NumberFormatException.class, () -> reader("-129").readOptionalByte());
    }

    @Test
    void readByteArray() {
        assertEquals(Arrays.asList(null, (byte) 0, (byte) 10, (byte) 100),
                reader("[null, 0, 10, 100]").readByteArray().orElseThrow().collect(Collectors.toList()));
        assertEquals(0, reader("[]").readByteArray().orElseThrow().count());
        assertTrue(reader("null").readByteArray().isEmpty());
        assertThrows(ParsingException.class,
                () -> reader("[0,").readByteArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class,
                () -> reader("[true, 2]").readByteArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class, () -> reader("{0,").readByteArray());
    }

    @Test
    void readShort() {
        assertEquals((Short) (short) 0, reader("0").readShort());
        assertEquals((Short) (short) (-32_768), reader("-32768").readShort());
        assertEquals((Short) (short) (32_767), reader("32767").readShort());
        assertThrows(ParsingException.class, () -> reader("null").readShort());
        assertThrows(NumberFormatException.class, () -> reader("-32769").readShort());
        assertThrows(NumberFormatException.class, () -> reader("32768").readShort());
    }

    @Test
    void readNullableShort() {
        assertEquals((Short) (short) 10, reader("10").readNullableShort());
        assertEquals((Short) (short) (-32_768), reader("-32768").readNullableShort());
        assertEquals((Short) (short) (32_767), reader("32767").readNullableShort());
        assertNull(reader("null").readNullableShort());
        assertThrows(NumberFormatException.class, () -> reader("-32769").readShort());
        assertThrows(NumberFormatException.class, () -> reader("32768").readShort());
    }

    @Test
    void readOptionalShort() {
        assertEquals((Short) (short) (-12_300), reader("-12300").readOptionalShort().orElseThrow());
        assertEquals((Short) (short) (-32_768), reader("-32768").readOptionalShort().orElseThrow());
        assertEquals((Short) (short) (32_767), reader("32767").readOptionalShort().orElseThrow());
        assertTrue(reader("null").readOptionalShort().isEmpty());
        assertThrows(NumberFormatException.class, () -> reader("-32769").readOptionalShort());
        assertThrows(NumberFormatException.class, () -> reader("32768").readOptionalShort());
    }

    @Test
    void readShortArray() {
        assertEquals(Arrays.asList(null, (short) 0, (short) 10, (short) 1000),
                reader("[null, 0, 10, 1000]").readShortArray().orElseThrow().collect(Collectors.toList()));
        assertEquals(0, reader("[]").readShortArray().orElseThrow().count());
        assertTrue(reader("null").readShortArray().isEmpty());
        assertThrows(ParsingException.class,
                () -> reader("[0,").readShortArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class,
                () -> reader("[true, 2]").readShortArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class, () -> reader("{0,").readShortArray());
    }

    @Test
    void readInt() {
    }

    @Test
    void readNullableInt() {
    }

    @Test
    void readOptionalInteger() {
    }

    @Test
    void readIntegerArray() {
    }

    @Test
    void readLong() {
    }

    @Test
    void readNullableLong() {
    }

    @Test
    void readOptionalLong() {
    }

    @Test
    void readLongArray() {
    }

    @Test
    void readFloat() {
    }

    @Test
    void readNullableFloat() {
    }

    @Test
    void readOptionalFloat() {
    }

    @Test
    void readFloatArray() {
    }

    @Test
    void readDouble() {
    }

    @Test
    void readNullableDouble() {
    }

    @Test
    void readOptionalDouble() {
    }

    @Test
    void readDoubleArray() {
    }

    @Test
    void readBigInteger() {
    }

    @Test
    void readNullableBigInteger() {
    }

    @Test
    void readOptionalBigInteger() {
    }

    @Test
    void readBigIntegerArray() {
    }

    @Test
    void readBigDecimal() {
    }

    @Test
    void readNullableBigDecimal() {
    }

    @Test
    void readOptionalBigDecimal() {
    }

    @Test
    void readBigDecimalArray() {
    }

    @Test
    void readString() {
    }

    @Test
    void readNullableString() {
    }

    @Test
    void readOptionalString() {
    }

    @Test
    void readStringArray() {
    }

    @Test
    void readChar() {
    }

    @Test
    void readNullableChar() {
    }

    @Test
    void readOptionalChar() {
    }

    @Test
    void readCharArray() {
    }

    @Test
    void read() {
    }

    @Test
    void readArray() {
    }

    @Test
    void readValue() {
    }

    @Test
    void readValueArray() {
    }

    @Test
    void readObject() {
    }

    @Test
    void readObjectArray() {
    }
}