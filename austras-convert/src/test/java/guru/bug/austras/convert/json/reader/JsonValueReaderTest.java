package guru.bug.austras.convert.json.reader;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;
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
    void readOptionalBooleanTrue() {
        var opt = reader("true").readOptionalBoolean();
        assertTrue(opt.isPresent());
        assertTrue(opt.get());
    }

    @Test
    void readOptionalBooleanFalse() {
        var opt = reader("false").readOptionalBoolean();
        assertTrue(opt.isPresent());
        assertFalse(opt.get());
    }

    @Test
    void readOptionalBooleanNull() {
        var opt = reader("null").readOptionalBoolean();
        assertTrue(opt.isEmpty());
    }

    @Test
    void readOptionalBooleanThrows() {
        assertThrows(ParsingException.class, () -> reader("\"true\"").readOptionalBoolean());
    }

    @Test
    void readBooleanArray() {
        var actual = reader("[true, false, null]").readBooleanArray();
        assertTrue(actual.isPresent());
        List<Boolean> expected = new java.util.ArrayList<>();
        expected.add(true);
        expected.add(false);
        expected.add(null);
        assertEquals(expected, actual.get().collect(Collectors.toList()));
    }

    @Test
    void readBooleanArrayEmpty() {
        var actual = reader("[]").readBooleanArray();
        assertTrue(actual.isPresent());
        assertEquals(0, actual.get().count());
    }

    @Test
    void readBooleanArrayNull() {
        var actual = reader("null").readBooleanArray();
        assertTrue(actual.isEmpty());
    }

    @Test
    void readBooleanArrayNotClosed() {
        var actual = reader("[true,").readBooleanArray();
        assertTrue(actual.isPresent());
        assertThrows(ParsingException.class, () -> actual.get().forEach(b -> {
        }));
    }

    @Test
    void readBooleanArrayNotBoolean() {
        var actual = reader("[true, 2]").readBooleanArray();
        assertTrue(actual.isPresent());
        assertThrows(ParsingException.class, () -> actual.get().forEach(b -> {
        }));
    }

    @Test
    void readBooleanArrayNotArray() {
        assertThrows(ParsingException.class, () -> reader("{true,").readBooleanArray());
    }

    @Test
    void readByte() {
    }

    @Test
    void readNullableByte() {
    }

    @Test
    void readOptionalByte() {
    }

    @Test
    void readByteArray() {
    }

    @Test
    void readShort() {
    }

    @Test
    void readNullableShort() {
    }

    @Test
    void readOptionalShort() {
    }

    @Test
    void readShortArray() {
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