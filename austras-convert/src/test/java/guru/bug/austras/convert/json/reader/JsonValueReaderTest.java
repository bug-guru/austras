package guru.bug.austras.convert.json.reader;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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
        assertEquals(0, reader("0").readInt());
        assertEquals(-2_147_483_648, reader("-2147483648").readInt());
        assertEquals(2_147_483_647, reader("2147483647").readInt());
        assertThrows(ParsingException.class, () -> reader("null").readInt());
        assertThrows(NumberFormatException.class, () -> reader("-2147483649").readInt());
        assertThrows(NumberFormatException.class, () -> reader("2147483648").readInt());
    }

    @Test
    void readNullableInt() {
        assertEquals(10, reader("10").readNullableInt());
        assertEquals(-2_147_483_648, reader("-2147483648").readNullableInt());
        assertEquals(2_147_483_647, reader("2147483647").readNullableInt());
        assertNull(reader("null").readNullableInt());
        assertThrows(NumberFormatException.class, () -> reader("-2147483649").readInt());
        assertThrows(NumberFormatException.class, () -> reader("2147483648").readInt());
    }

    @Test
    void readOptionalInt() {
        assertEquals(-12_300, reader("-12300").readOptionalInteger().orElseThrow());
        assertEquals(-2_147_483_648, reader("-2147483648").readOptionalInteger().orElseThrow());
        assertEquals(2_147_483_647, reader("2147483647").readOptionalInteger().orElseThrow());
        assertTrue(reader("null").readOptionalInteger().isEmpty());
        assertThrows(NumberFormatException.class, () -> reader("-2147483649").readOptionalInteger());
        assertThrows(NumberFormatException.class, () -> reader("2147483648").readOptionalInteger());
    }

    @Test
    void readIntArray() {
        assertEquals(Arrays.asList(null, 0, 10, 1000),
                reader("[null, 0, 10, 1000]").readIntegerArray().orElseThrow().collect(Collectors.toList()));
        assertEquals(0, reader("[]").readIntegerArray().orElseThrow().count());
        assertTrue(reader("null").readIntegerArray().isEmpty());
        assertThrows(ParsingException.class,
                () -> reader("[0,").readIntegerArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class,
                () -> reader("[true, 2]").readIntegerArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class, () -> reader("{0,").readIntegerArray());
    }

    @Test
    void readLong() {
        assertEquals(0, reader("0").readLong());
        assertEquals(-9_223_372_036_854_775_808L, reader("-9223372036854775808").readLong());
        assertEquals(9_223_372_036_854_775_807L, reader("9223372036854775807").readLong());
        assertThrows(ParsingException.class, () -> reader("null").readLong());
        assertThrows(NumberFormatException.class, () -> reader("-9223372036854775809").readLong());
        assertThrows(NumberFormatException.class, () -> reader("9223372036854775808").readLong());
    }

    @Test
    void readNullableLong() {
        assertEquals(10, reader("10").readNullableLong());
        assertEquals(-9_223_372_036_854_775_808L, reader("-9223372036854775808").readNullableLong());
        assertEquals(9_223_372_036_854_775_807L, reader("9223372036854775807").readNullableLong());
        assertNull(reader("null").readNullableLong());
        assertThrows(NumberFormatException.class, () -> reader("-9223372036854775809").readLong());
        assertThrows(NumberFormatException.class, () -> reader("9223372036854775808").readLong());
    }

    @Test
    void readOptionalLong() {
        assertEquals(-12_300, reader("-12300").readOptionalLong().orElseThrow());
        assertEquals(-9_223_372_036_854_775_808L, reader("-9223372036854775808").readOptionalLong().orElseThrow());
        assertEquals(9_223_372_036_854_775_807L, reader("9223372036854775807").readOptionalLong().orElseThrow());
        assertTrue(reader("null").readOptionalLong().isEmpty());
        assertThrows(NumberFormatException.class, () -> reader("-9223372036854775809").readOptionalLong());
        assertThrows(NumberFormatException.class, () -> reader("9223372036854775808").readOptionalLong());
    }

    @Test
    void readLongArray() {
        assertEquals(Arrays.asList(null, 0L, 10L, 1000L),
                reader("[null, 0, 10, 1000]").readLongArray().orElseThrow().collect(Collectors.toList()));
        assertEquals(0, reader("[]").readLongArray().orElseThrow().count());
        assertTrue(reader("null").readLongArray().isEmpty());
        assertThrows(ParsingException.class,
                () -> reader("[0,").readLongArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class,
                () -> reader("[true, 2]").readLongArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class, () -> reader("{0,").readLongArray());
    }

    @Test
    void readFloat() {
        assertEquals(0, reader("0").readFloat());
        assertEquals(-123_456.7F, reader("-123456.7").readFloat());
        assertEquals(76_543.21F, reader("76543.21").readFloat());
        assertEquals(Float.MIN_VALUE, reader(Float.toString(Float.MIN_VALUE)).readFloat());
        assertEquals(Float.MAX_VALUE, reader(Float.toString(Float.MAX_VALUE)).readFloat());
        assertThrows(ParsingException.class, () -> reader("null").readFloat());
        assertThrows(ParsingException.class, () -> reader("[").readFloat());
        assertTrue(Float.isInfinite(reader(Double.toString(Double.MAX_VALUE)).readFloat()));
        assertEquals(0F, reader(Double.toString(Double.MIN_VALUE)).readFloat());
    }

    @Test
    void readNullableFloat() {
        assertEquals(-123_456.7F, reader("-123456.7").readNullableFloat());
        assertEquals(76_543.21F, reader("76543.21").readNullableFloat());
        assertEquals(Float.MIN_VALUE, reader(Float.toString(Float.MIN_VALUE)).readNullableFloat());
        assertEquals(Float.MAX_VALUE, reader(Float.toString(Float.MAX_VALUE)).readNullableFloat());
        assertNull(reader("null").readNullableFloat());
        assertThrows(ParsingException.class, () -> reader("[").readNullableFloat());
        assertTrue(Float.isInfinite(reader(Double.toString(Double.MAX_VALUE)).readNullableFloat()));
        assertEquals(0F, reader(Double.toString(Double.MIN_VALUE)).readNullableFloat());
    }

    @Test
    void readOptionalFloat() {
        assertEquals(-123_456.7F, reader("-123456.7").readOptionalFloat().orElseThrow());
        assertEquals(76_543.21F, reader("76543.21").readOptionalFloat().orElseThrow());
        assertEquals(Float.MIN_VALUE, reader(Float.toString(Float.MIN_VALUE)).readOptionalFloat().orElseThrow());
        assertEquals(Float.MAX_VALUE, reader(Float.toString(Float.MAX_VALUE)).readOptionalFloat().orElseThrow());
        assertTrue(reader("null").readOptionalFloat().isEmpty());
        assertThrows(ParsingException.class, () -> reader("[").readOptionalFloat().orElseThrow());
        assertTrue(Float.isInfinite(reader(Double.toString(Double.MAX_VALUE)).readOptionalFloat().orElseThrow()));
        assertEquals(0F, reader(Double.toString(Double.MIN_VALUE)).readOptionalFloat().orElseThrow());
    }

    @Test
    void readFloatArray() {
        assertEquals(Arrays.asList(null, 0F, 10F, 1000F),
                reader("[null, 0, 10, 1000]").readFloatArray().orElseThrow().collect(Collectors.toList()));
        assertEquals(0, reader("[]").readFloatArray().orElseThrow().count());
        assertTrue(reader("null").readFloatArray().isEmpty());
        assertThrows(ParsingException.class,
                () -> reader("[0,").readFloatArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class,
                () -> reader("[true, 2]").readFloatArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class, () -> reader("{0,").readFloatArray());
    }

    @Test
    void readDouble() {
        assertEquals(0.0, reader("0").readDouble());
        assertEquals(0.0, reader("0.0").readDouble());
        assertEquals(-123_456.7, reader("-123456.7").readDouble());
        assertEquals(76_543.21, reader("76543.21").readDouble());
        assertEquals(Double.MIN_VALUE, reader(Double.toString(Double.MIN_VALUE)).readDouble());
        assertEquals(Double.MAX_VALUE, reader(Double.toString(Double.MAX_VALUE)).readDouble());
        assertThrows(ParsingException.class, () -> reader("null").readDouble());
        assertThrows(ParsingException.class, () -> reader("[").readDouble());
        assertTrue(Double.isInfinite(reader(new BigDecimal(Double.MAX_VALUE).multiply(BigDecimal.TEN).toString()).readDouble()));
        assertEquals(0.0, reader(new BigDecimal(Double.MIN_VALUE).divide(BigDecimal.TEN, RoundingMode.HALF_UP).toString()).readDouble());
    }

    @Test
    void readNullableDouble() {
        assertEquals(-123_456.7, reader("-123456.7").readNullableDouble());
        assertEquals(76_543.21, reader("76543.21").readNullableDouble());
        assertEquals(Double.MIN_VALUE, reader(Double.toString(Double.MIN_VALUE)).readNullableDouble());
        assertEquals(Double.MAX_VALUE, reader(Double.toString(Double.MAX_VALUE)).readNullableDouble());
        assertNull(reader("null").readNullableDouble());
        assertThrows(ParsingException.class, () -> reader("[").readNullableDouble());
        assertTrue(Double.isInfinite(reader(new BigDecimal(Double.MAX_VALUE).multiply(BigDecimal.TEN).toString()).readNullableDouble()));
        assertEquals(0.0, reader(new BigDecimal(Double.MIN_VALUE).divide(BigDecimal.TEN, RoundingMode.HALF_UP).toString()).readNullableDouble());
    }

    @Test
    void readOptionalDouble() {
        assertEquals(-123_456.7, reader("-123456.7").readOptionalDouble().orElseThrow());
        assertEquals(76_543.21, reader("76543.21").readOptionalDouble().orElseThrow());
        assertEquals(Double.MIN_VALUE, reader(Double.toString(Double.MIN_VALUE)).readOptionalDouble().orElseThrow());
        assertEquals(Double.MAX_VALUE, reader(Double.toString(Double.MAX_VALUE)).readOptionalDouble().orElseThrow());
        assertTrue(reader("null").readOptionalDouble().isEmpty());
        assertThrows(ParsingException.class, () -> reader("[").readOptionalDouble().orElseThrow());
        assertTrue(Double.isInfinite(reader(new BigDecimal(Double.MAX_VALUE).multiply(BigDecimal.TEN).toString()).readOptionalDouble().orElseThrow()));
        assertEquals(0.0, reader(new BigDecimal(Double.MIN_VALUE).divide(BigDecimal.TEN, RoundingMode.HALF_UP).toString()).readOptionalDouble().orElseThrow());
    }

    @Test
    void readDoubleArray() {
        assertEquals(Arrays.asList(null, 0.0, 10.0, 1000.0),
                reader("[null, 0, 10, 1000]").readDoubleArray().orElseThrow().collect(Collectors.toList()));
        assertEquals(0, reader("[]").readDoubleArray().orElseThrow().count());
        assertTrue(reader("null").readDoubleArray().isEmpty());
        assertThrows(ParsingException.class,
                () -> reader("[0,").readDoubleArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class,
                () -> reader("[true, 2]").readDoubleArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class, () -> reader("{0,").readDoubleArray());
    }

    @Test
    void readBigInteger() {
        assertEquals(BigInteger.ZERO, reader("0").readBigInteger());
        assertEquals(BigInteger.valueOf(-2_147_483_648), reader("-2147483648").readBigInteger());
        assertEquals(BigInteger.valueOf(2_147_483_647), reader("2147483647").readBigInteger());
        assertThrows(ParsingException.class, () -> reader("null").readBigInteger());
    }

    @Test
    void readNullableBigInteger() {
        assertEquals(BigInteger.ZERO, reader("0").readNullableBigInteger());
        assertEquals(BigInteger.valueOf(-2_147_483_648), reader("-2147483648").readNullableBigInteger());
        assertEquals(BigInteger.valueOf(2_147_483_647), reader("2147483647").readNullableBigInteger());
        assertNull(reader("null").readNullableBigInteger());
    }


    @Test
    void readOptionalBigInteger() {
        assertEquals(BigInteger.ZERO, reader("0").readOptionalBigInteger().orElseThrow());
        assertEquals(BigInteger.valueOf(-2_147_483_648), reader("-2147483648").readOptionalBigInteger().orElseThrow());
        assertEquals(BigInteger.valueOf(2_147_483_647), reader("2147483647").readOptionalBigInteger().orElseThrow());
        assertTrue(reader("null").readOptionalBigInteger().isEmpty());
    }

    @Test
    void readBigIntegerArray() {
        assertEquals(Arrays.asList(null, 0, 10, 1000),
                reader("[null, 0, 10, 1000]").readIntegerArray().orElseThrow().collect(Collectors.toList()));
        assertEquals(0, reader("[]").readIntegerArray().orElseThrow().count());
        assertTrue(reader("null").readIntegerArray().isEmpty());
        assertThrows(ParsingException.class,
                () -> reader("[0,").readIntegerArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class,
                () -> reader("[true, 2]").readIntegerArray().orElseThrow().forEach(this::noop));
        assertThrows(ParsingException.class, () -> reader("{0,").readIntegerArray());
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