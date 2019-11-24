package guru.bug.austras.convert.json.writer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonValueWriterTest {
    private static final String SAMPLE_ARAY_JSON = "[{\"key1\":\"aa\",\"key2\":1,\"key3\":\"a\"}," +
            "{\"key1\":\"bb\",\"key2\":2,\"key3\":\"b\"}," +
            "{\"key1\":\"cc\",\"key2\":3,\"key3\":\"c\"}," +
            "null," +
            "{\"key1\":\"ee\",\"key2\":5,\"key3\":\"e\"}]";
    private static final SampleObject[] SAMPLE_ARRAY = new SampleObject[]{
            new SampleObject("aa" , 1, 'a'),
            new SampleObject("bb" , 2, 'b'),
            new SampleObject("cc" , 3, 'c'),
            null,
            new SampleObject("ee" , 5, 'e'),
    };
    private JsonValueWriter jsonValueWriter;
    private StringWriter result;

    @BeforeEach
    void initEach() {
        result = new StringWriter();
        var tokenWriter = new JsonTokenWriter(result);
        jsonValueWriter = new JsonValueWriterImpl(tokenWriter);
    }

    @Test
    void testWriteNull() {
        jsonValueWriter.writeNull();
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteByteMin() {
        jsonValueWriter.write(Byte.MIN_VALUE);
        assertEquals(String.valueOf(Byte.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteByteMax() {
        jsonValueWriter.write(Byte.MAX_VALUE);
        assertEquals(String.valueOf(Byte.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjByteMin() {
        jsonValueWriter.write((Byte) Byte.MIN_VALUE);
        assertEquals(String.valueOf(Byte.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteObjByteMax() {
        jsonValueWriter.write((Byte) Byte.MAX_VALUE);
        assertEquals(String.valueOf(Byte.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjByteNull() {
        jsonValueWriter.write((Byte) null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteShortMin() {
        jsonValueWriter.write(Short.MIN_VALUE);
        assertEquals(String.valueOf(Short.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteShortMax() {
        jsonValueWriter.write(Short.MAX_VALUE);
        assertEquals(String.valueOf(Short.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjShortMin() {
        jsonValueWriter.write((Short) Short.MIN_VALUE);
        assertEquals(String.valueOf(Short.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteObjShortMax() {
        jsonValueWriter.write((Short) Short.MAX_VALUE);
        assertEquals(String.valueOf(Short.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjShortNull() {
        jsonValueWriter.write((Short) null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteIntMin() {
        jsonValueWriter.write(Integer.MIN_VALUE);
        assertEquals(String.valueOf(Integer.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteIntMax() {
        jsonValueWriter.write(Integer.MAX_VALUE);
        assertEquals(String.valueOf(Integer.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjIntMin() {
        jsonValueWriter.write((Integer) Integer.MIN_VALUE);
        assertEquals(String.valueOf(Integer.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteObjIntMax() {
        jsonValueWriter.write((Integer) Integer.MAX_VALUE);
        assertEquals(String.valueOf(Integer.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjIntNull() {
        jsonValueWriter.write((Integer) null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteLongMin() {
        jsonValueWriter.write(Long.MIN_VALUE);
        assertEquals(String.valueOf(Long.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteLongMax() {
        jsonValueWriter.write(Long.MAX_VALUE);
        assertEquals(String.valueOf(Long.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjLongMin() {
        jsonValueWriter.write((Long) Long.MIN_VALUE);
        assertEquals(String.valueOf(Long.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteObjLongMax() {
        jsonValueWriter.write((Long) Long.MAX_VALUE);
        assertEquals(String.valueOf(Long.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjLongNull() {
        jsonValueWriter.write((Long) null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteFloatNegativeInfinitive() {
        jsonValueWriter.write(Float.NEGATIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteFloatPositiveInfinitive() {
        jsonValueWriter.write(Float.POSITIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteFloatNaN() {
        jsonValueWriter.write(Float.NaN);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteFloatMin() {
        jsonValueWriter.write(Float.MIN_VALUE);
        assertEquals(String.valueOf(Float.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteFloatMax() {
        jsonValueWriter.write(Float.MAX_VALUE);
        assertEquals(String.valueOf(Float.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjFloatNegativeInfinitive() {
        jsonValueWriter.write((Float) Float.NEGATIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteObjFloatPositiveInfinitive() {
        jsonValueWriter.write((Float) Float.POSITIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteObjFloatNaN() {
        jsonValueWriter.write((Float) Float.NaN);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteObjFloatMin() {
        jsonValueWriter.write((Float) Float.MIN_VALUE);
        assertEquals(String.valueOf(Float.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteObjFloatMax() {
        jsonValueWriter.write((Float) Float.MAX_VALUE);
        assertEquals(String.valueOf(Float.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjFloatNull() {
        jsonValueWriter.write((Float) null);
        assertEquals("null" , result.toString());
    }


    @Test
    void testWriteDoubleNegativeInfinitive() {
        jsonValueWriter.write(Double.NEGATIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteDoublePositiveInfinitive() {
        jsonValueWriter.write(Double.POSITIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteDoubleNaN() {
        jsonValueWriter.write(Double.NaN);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteDoubleMin() {
        jsonValueWriter.write(Double.MIN_VALUE);
        assertEquals(String.valueOf(Double.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteDoubleMax() {
        jsonValueWriter.write(Double.MAX_VALUE);
        assertEquals(String.valueOf(Double.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjDoubleNegativeInfinitive() {
        jsonValueWriter.write((Double) Double.NEGATIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteObjDoublePositiveInfinitive() {
        jsonValueWriter.write((Double) Double.POSITIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteObjDoubleNaN() {
        jsonValueWriter.write((Double) Double.NaN);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteObjDoubleMin() {
        jsonValueWriter.write((Double) Double.MIN_VALUE);
        assertEquals(String.valueOf(Double.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteObjDoubleMax() {
        jsonValueWriter.write((Double) Double.MAX_VALUE);
        assertEquals(String.valueOf(Double.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjDoubleNull() {
        jsonValueWriter.write((Double) null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteBooleanFalse() {
        jsonValueWriter.write(false);
        assertEquals("false" , result.toString());
    }

    @Test
    void testWriteBooleanTrue() {
        jsonValueWriter.write(true);
        assertEquals("true" , result.toString());
    }

    @Test
    void testWriteObjBooleanFalse() {
        jsonValueWriter.write((Boolean) false);
        assertEquals("false" , result.toString());
    }

    @Test
    void testWriteObjBooleanTrue() {
        jsonValueWriter.write((Boolean) true);
        assertEquals("true" , result.toString());
    }

    @Test
    void testWriteObjBooleanNull() {
        jsonValueWriter.write((Boolean) null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteBigDecimal() {
        jsonValueWriter.write(BigDecimal.TEN);
        assertEquals(String.valueOf(BigDecimal.TEN), result.toString());
    }

    @Test
    void testWriteBigInteger() {
        jsonValueWriter.write(BigInteger.TEN);
        assertEquals(String.valueOf(BigInteger.TEN), result.toString());
    }

    @Test
    void testWriteBigDecimalNull() {
        jsonValueWriter.write((BigDecimal) null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteBigIntegerNull() {
        jsonValueWriter.write((BigInteger) null);
        assertEquals("null" , result.toString());
    }


    @Test
    void testWriteChar() {
        jsonValueWriter.write('a');
        assertEquals("\"a\"" , result.toString());
    }

    @Test
    void testWriteObjChar() {
        jsonValueWriter.write((Character) 'a');
        assertEquals("\"a\"" , result.toString());
    }

    @Test
    void testWriteObjCharNull() {
        jsonValueWriter.write((Character) null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteCharEscape() {
        jsonValueWriter.write('"');
        assertEquals("\"\\\"\"" , result.toString());
    }

    @Test
    void testWriteStringNull() {
        jsonValueWriter.write((String) null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteEmptyString() {
        jsonValueWriter.write("");
        assertEquals("\"\"" , result.toString());
    }

    @Test
    void testWriteString() {
        jsonValueWriter.write("abc");
        assertEquals("\"abc\"" , result.toString());
    }

    @Test
    void testWriteRawString() {
        jsonValueWriter.writeRaw("unescaped\"\n");
        assertEquals("unescaped\"\n" , result.toString());
    }

    @Test
    void testWriteObjectNull() {
        jsonValueWriter.writeObject(null, (o, ow) -> ow.write("a" , null, (o2, vw) -> vw.write("abc")));
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteSimpleObject() {
        jsonValueWriter.writeObject("" , (object, writer) -> {
            writer.write("key1" , "string" , this::write);
            writer.write("key2" , 1234, this::writeInt);
            writer.write("key3" , 'x', this::writeChar);
        });
        assertEquals("{\"key1\":\"string\",\"key2\":1234,\"key3\":\"x\"}" , result.toString());
    }

    private void write(String str, JsonValueWriter valueWriter) {
        valueWriter.write(str);
    }

    private void writeInt(int i, JsonValueWriter valueWriter) {
        valueWriter.write(i);
    }

    private void writeChar(char ch, JsonValueWriter valueWriter) {
        valueWriter.write(ch);
    }

    private void writeInts(List<Integer> integers, JsonValueWriter valueWriter) {
        valueWriter.writeArray(integers, this::writeInt);
    }

    private void writeStrings(List<String> strings, JsonValueWriter valueWriter) {
        valueWriter.writeArray(strings, this::write);
    }

    private void writeSampleObject(SampleObject obj, JsonValueWriter valueWriter) {
        valueWriter.writeObject(obj, (o, ow) -> {
            ow.write("key1" , o.getKey1(), this::write);
            ow.write("key2" , o.getKey2(), this::writeInt);
            ow.write("key3" , o.getKey3(), this::writeChar);
        });
    }

    private void writeSampleObject(List<SampleObject> sampleObjects, JsonValueWriter valueWriter) {
        valueWriter.writeArray(sampleObjects, this::writeSampleObject);
    }

    @Test
    void testWriteComplexObject() {
        var sample = new SampleObject();
        jsonValueWriter.writeObject("" , (object, writer) -> {
            writer.write("a" , 5, this::writeInt);
            writer.write("obj" , sample, this::writeSampleObject);
            writer.write("ints" , List.of(1, 2, 3), this::writeInts);
            writer.write("objects" , List.of(sample, sample), this::writeSampleObject);
        });
        String jsonSample = "{\"key1\":\"string\",\"key2\":1234,\"key3\":\"x\"}";
        assertEquals("{\"a\":5,\"obj\":" + jsonSample + ",\"ints\":[1,2,3],\"objects\":[" + jsonSample + "," + jsonSample + "]}" , result.toString());
    }

    @Test
    void testWriteNullArray() {
        jsonValueWriter.writeArray((String[]) null, this::write);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteArrayWithNulls() {
        jsonValueWriter.writeArray(new String[]{null, "a" , null, "b" , null}, this::write);
        assertEquals("[null,\"a\",null,\"b\",null]" , result.toString());
    }

    @Test
    void testWriteArrayAsStream() {
        Stream<String> stream = Stream.empty();
        jsonValueWriter.writeArray(stream, this::write);
        assertEquals("[]" , result.toString());
    }

    private void writeSampleObject(SampleObject obj, JsonObjectWriter writer) {
        writer.write("key1" , obj.getKey1());
        writer.write("key2" , obj.getKey2());
        writer.write("key3" , obj.getKey3());
    }

    @Test
    void writeObjectArray_Array() {
        jsonValueWriter.writeObjectArray(SAMPLE_ARRAY, this::writeSampleObject);
        assertEquals(SAMPLE_ARAY_JSON, result.toString());
    }

    @Test
    void writeObjectArray_Stream() {
        jsonValueWriter.writeObjectArray(Stream.of(SAMPLE_ARRAY), this::writeSampleObject);
        assertEquals(SAMPLE_ARAY_JSON, result.toString());
    }

    @Test
    void writeObjectArray_Iterable() {
        jsonValueWriter.writeObjectArray(Arrays.asList(SAMPLE_ARRAY), this::writeSampleObject);
        assertEquals(SAMPLE_ARAY_JSON, result.toString());
    }

    @Test
    void writeObjectArray_Iterator() {
        jsonValueWriter.writeObjectArray(Arrays.asList(SAMPLE_ARRAY).iterator(), this::writeSampleObject);
        assertEquals(SAMPLE_ARAY_JSON, result.toString());
    }

    private static class SampleObject {
        String key1 = "string";
        int key2 = 1234;
        char key3 = 'x';

        SampleObject(String key1, int key2, char key3) {
            this.key1 = key1;
            this.key2 = key2;
            this.key3 = key3;
        }

        SampleObject() {
        }

        String getKey1() {
            return key1;
        }

        int getKey2() {
            return key2;
        }

        char getKey3() {
            return key3;
        }
    }


}
