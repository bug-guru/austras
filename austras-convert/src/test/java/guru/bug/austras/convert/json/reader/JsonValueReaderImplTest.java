package guru.bug.austras.convert.json.reader;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class JsonValueReaderImplTest {

    private JsonValueReader reader(String json) {
        var strReader = new StringReader(json);
        var tokenReader = new JsonTokenReader(strReader);
        return new JsonValueReaderImpl(tokenReader);
    }

    @Test
    void readBoolean() {
        assertTrue(reader("true").readBoolean());
        assertFalse(reader("false").readBoolean());
        assertThrows(ParsingException.class, () -> reader("null").readBoolean());
    }

    @Test
    void readNullableBoolean() {
    }

    @Test
    void readOptionalBoolean() {
    }

    @Test
    void readBooleanArray() {
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