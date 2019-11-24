package guru.bug.austras.convert.json.writer;

import guru.bug.austras.convert.json.utils.JsonWritingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class JsonObjectWriterTest {
    private static final String KEY = "\"key\":";
    private JsonObjectWriterImpl ow;
    private StringWriter out;

    @BeforeEach
    void initEach() {
        out = new StringWriter();
        var tokenWriter = new JsonTokenWriter(out);
        var valueWriter = new JsonValueWriterImpl(tokenWriter);
        ow = new JsonObjectWriterImpl(tokenWriter, valueWriter);
    }

    private String wrap(String str) {
        return "\"" + str + "\"";
    }

    private String keyValue(String key, String value) {
        return wrap(key) + ":" + value;
    }

    @Test
    void testCreateWithNullWriter() {
        var fakeJsonTokenWriter = new JsonTokenWriter(new StringWriter());
        var fakeJsonValueWriter = new JsonValueWriterImpl(fakeJsonTokenWriter);
        assertThrows(NullPointerException.class, () -> new JsonObjectWriterImpl(null, null));
        assertThrows(NullPointerException.class, () -> new JsonObjectWriterImpl(fakeJsonTokenWriter, null));
        assertThrows(NullPointerException.class, () -> new JsonObjectWriterImpl(null, fakeJsonValueWriter));
        assertDoesNotThrow(() -> new JsonObjectWriterImpl(fakeJsonTokenWriter, fakeJsonValueWriter));
    }

    @Test
    void testWriteNull() {
        ow.writeNull("nullValue");
        assertEquals(keyValue("nullValue" , "null"), out.toString());
    }

    @Test
    void testWriteDuplicateKey() {
        ow.write("key" , "abc" , this::write);
        assertThrows(JsonWritingException.class, () -> ow.write("key" , "xyz" , this::write));
    }

    @Test
    void testWriteNullKey() {
        assertThrows(NullPointerException.class, () -> ow.write(null, "xyz" , this::write));
    }

    @Test
    void testWriteNullValue() {
        ow.write("key" , null, this::write);
        assertEquals(keyValue("key" , "null"), out.toString());
    }

    private void write(String str, JsonValueWriter valueWriter) {
        valueWriter.write(str);
    }

    @Test
    void testWriteSimpleObject() {
        ow.writeBegin();
        ow.write("key1" , 1);
        ow.write("key2" , "string");
        ow.writeNull("key3");
        ow.writeEnd();
        assertEquals("{\"key1\":1,\"key2\":\"string\",\"key3\":null}" , out.toString());
    }

    @Test
    void testWriteObject() {
        ow.writeObject("key1" , "x" , (x, w) -> {
            w.write("key1" , 1);
            w.write("key2" , "string");
            w.writeNull("key3");
        });
        assertEquals("\"key1\":{\"key1\":1,\"key2\":\"string\",\"key3\":null}" , out.toString());

    }

    @Test
    void testWriteObject_null() {
        ow.writeObject("obj" , null, (x, w) -> {
            w.write("key1" , 1);
            w.write("key2" , "string");
            w.writeNull("key3");
        });
        assertEquals("\"obj\":null" , out.toString());
    }

    @Test
    void testWriteObjectArray_array() {
        ow.writeObjectArray("array" , new String[]{"a" , "b" , "c"}, (obj, w) -> w.write("key" , obj));
        assertEquals("\"array\":[{\"key\":\"a\"},{\"key\":\"b\"},{\"key\":\"c\"}]" , out.toString());
    }

    @Test
    void testWriteObjectArray_stream() {
        ow.writeObjectArray("array" , Stream.of("a" , "b" , "c"), (obj, w) -> w.write("key" , obj));
        assertEquals("\"array\":[{\"key\":\"a\"},{\"key\":\"b\"},{\"key\":\"c\"}]" , out.toString());
    }

    @Test
    void testWriteObjectArray_iterable() {
        ow.writeObjectArray("array" , Arrays.asList("a" , "b" , "c"), (obj, w) -> w.write("key" , obj));
        assertEquals("\"array\":[{\"key\":\"a\"},{\"key\":\"b\"},{\"key\":\"c\"}]" , out.toString());
    }

    @Test
    void testWriteObjectArray_iterator() {
        ow.writeObjectArray("array" , Arrays.asList("a" , "b" , "c").iterator(), (obj, w) -> w.write("key" , obj));
        assertEquals("\"array\":[{\"key\":\"a\"},{\"key\":\"b\"},{\"key\":\"c\"}]" , out.toString());
    }

    @Test
    void testWriteArray_array() {
        ow.writeArray("array" , new String[]{"a" , "b" , "c"}, (obj, w) -> w.write(obj));
        assertEquals("\"array\":[\"a\",\"b\",\"c\"]" , out.toString());
    }

    @Test
    void testWriteArray_array_null() {
        ow.writeArray("array" , (String[]) null, (obj, w) -> w.write(obj));
        assertEquals("\"array\":null" , out.toString());
    }

    @Test
    void testWriteArray_stream() {
        ow.writeArray("array" , Stream.of("a" , "b" , "c"), (obj, w) -> w.write(obj));
        assertEquals("\"array\":[\"a\",\"b\",\"c\"]" , out.toString());
    }

    @Test
    void testWriteArray_stream_null() {
        ow.writeArray("array" , (Stream<String>) null, (obj, w) -> w.write(obj));
        assertEquals("\"array\":null" , out.toString());
    }

    @Test
    void testWriteArray_iterable() {
        ow.writeArray("array" , List.of("a" , "b" , "c"), (obj, w) -> w.write(obj));
        assertEquals("\"array\":[\"a\",\"b\",\"c\"]" , out.toString());
    }

    @Test
    void testWriteArray_iterable_null() {
        ow.writeArray("array" , (List<String>) null, (obj, w) -> w.write(obj));
        assertEquals("\"array\":null" , out.toString());
    }

    @Test
    void testWriteArray_iterator() {
        ow.writeArray("array" , List.of("a" , "b" , "c").iterator(), (obj, w) -> w.write(obj));
        assertEquals("\"array\":[\"a\",\"b\",\"c\"]" , out.toString());
    }

    @Test
    void testWriteArray_iterator_null() {
        ow.writeArray("array" , (Iterator<String>) null, (obj, w) -> w.write(obj));
        assertEquals("\"array\":null" , out.toString());
    }

    @Test
    void testWriteByteMin() {
        ow.write("key" , Byte.MIN_VALUE);
        assertEquals(KEY + Byte.MIN_VALUE, out.toString());
    }

    @Test
    void testWriteByteMax() {
        ow.write("key" , Byte.MAX_VALUE);
        assertEquals(KEY + Byte.MAX_VALUE, out.toString());
    }

    @Test
    void testWriteObjByteMin() {
        ow.write("key" , (Byte) Byte.MIN_VALUE);
        assertEquals(KEY + Byte.MIN_VALUE, out.toString());
    }

    @Test
    void testWriteObjByteMax() {
        ow.write("key" , (Byte) Byte.MAX_VALUE);
        assertEquals(KEY + Byte.MAX_VALUE, out.toString());
    }

    @Test
    void testWriteObjByteNull() {
        ow.write("key" , (Byte) null);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteShortMin() {
        ow.write("key" , Short.MIN_VALUE);
        assertEquals(KEY + Short.MIN_VALUE, out.toString());
    }

    @Test
    void testWriteShortMax() {
        ow.write("key" , Short.MAX_VALUE);
        assertEquals(KEY + Short.MAX_VALUE, out.toString());
    }

    @Test
    void testWriteObjShortMin() {
        ow.write("key" , (Short) Short.MIN_VALUE);
        assertEquals(KEY + Short.MIN_VALUE, out.toString());
    }

    @Test
    void testWriteObjShortMax() {
        ow.write("key" , (Short) Short.MAX_VALUE);
        assertEquals(KEY + Short.MAX_VALUE, out.toString());
    }

    @Test
    void testWriteObjShortNull() {
        ow.write("key" , (Short) null);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteIntMin() {
        ow.write("key" , Integer.MIN_VALUE);
        assertEquals(KEY + Integer.MIN_VALUE, out.toString());
    }

    @Test
    void testWriteIntMax() {
        ow.write("key" , Integer.MAX_VALUE);
        assertEquals(KEY + Integer.MAX_VALUE, out.toString());
    }

    @Test
    void testWriteObjIntMin() {
        ow.write("key" , (Integer) Integer.MIN_VALUE);
        assertEquals(KEY + Integer.MIN_VALUE, out.toString());
    }

    @Test
    void testWriteObjIntMax() {
        ow.write("key" , (Integer) Integer.MAX_VALUE);
        assertEquals(KEY + Integer.MAX_VALUE, out.toString());
    }

    @Test
    void testWriteObjIntNull() {
        ow.write("key" , (Integer) null);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteLongMin() {
        ow.write("key" , Long.MIN_VALUE);
        assertEquals(KEY + Long.MIN_VALUE, out.toString());
    }

    @Test
    void testWriteLongMax() {
        ow.write("key" , Long.MAX_VALUE);
        assertEquals(KEY + Long.MAX_VALUE, out.toString());
    }

    @Test
    void testWriteObjLongMin() {
        ow.write("key" , (Long) Long.MIN_VALUE);
        assertEquals(KEY + Long.MIN_VALUE, out.toString());
    }

    @Test
    void testWriteObjLongMax() {
        ow.write("key" , (Long) Long.MAX_VALUE);
        assertEquals(KEY + Long.MAX_VALUE, out.toString());
    }

    @Test
    void testWriteObjLongNull() {
        ow.write("key" , (Long) null);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteFloatNegativeInfinitive() {
        ow.write("key" , Float.NEGATIVE_INFINITY);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteFloatPositiveInfinitive() {
        ow.write("key" , Float.POSITIVE_INFINITY);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteFloatNaN() {
        ow.write("key" , Float.NaN);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteFloatMin() {
        ow.write("key" , Float.MIN_VALUE);
        assertEquals(KEY + Float.MIN_VALUE, out.toString());
    }

    @Test
    void testWriteFloatMax() {
        ow.write("key" , Float.MAX_VALUE);
        assertEquals(KEY + Float.MAX_VALUE, out.toString());
    }

    @Test
    void testWriteObjFloatNegativeInfinitive() {
        ow.write("key" , (Float) Float.NEGATIVE_INFINITY);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteObjFloatPositiveInfinitive() {
        ow.write("key" , (Float) Float.POSITIVE_INFINITY);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteObjFloatNaN() {
        ow.write("key" , (Float) Float.NaN);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteObjFloatMin() {
        ow.write("key" , (Float) Float.MIN_VALUE);
        assertEquals(KEY + Float.MIN_VALUE, out.toString());
    }

    @Test
    void testWriteObjFloatMax() {
        ow.write("key" , (Float) Float.MAX_VALUE);
        assertEquals(KEY + Float.MAX_VALUE, out.toString());
    }

    @Test
    void testWriteObjFloatNull() {
        ow.write("key" , (Float) null);
        assertEquals(KEY + "null" , out.toString());
    }


    @Test
    void testWriteDoubleNegativeInfinitive() {
        ow.write("key" , Double.NEGATIVE_INFINITY);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteDoublePositiveInfinitive() {
        ow.write("key" , Double.POSITIVE_INFINITY);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteDoubleNaN() {
        ow.write("key" , Double.NaN);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteDoubleMin() {
        ow.write("key" , Double.MIN_VALUE);
        assertEquals(KEY + Double.MIN_VALUE, out.toString());
    }

    @Test
    void testWriteDoubleMax() {
        ow.write("key" , Double.MAX_VALUE);
        assertEquals(KEY + Double.MAX_VALUE, out.toString());
    }

    @Test
    void testWriteObjDoubleNegativeInfinitive() {
        ow.write("key" , (Double) Double.NEGATIVE_INFINITY);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteObjDoublePositiveInfinitive() {
        ow.write("key" , (Double) Double.POSITIVE_INFINITY);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteObjDoubleNaN() {
        ow.write("key" , (Double) Double.NaN);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteObjDoubleMin() {
        ow.write("key" , (Double) Double.MIN_VALUE);
        assertEquals(KEY + Double.MIN_VALUE, out.toString());
    }

    @Test
    void testWriteObjDoubleMax() {
        ow.write("key" , (Double) Double.MAX_VALUE);
        assertEquals(KEY + Double.MAX_VALUE, out.toString());
    }

    @Test
    void testWriteObjDoubleNull() {
        ow.write("key" , (Double) null);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteBooleanFalse() {
        ow.write("key" , false);
        assertEquals(KEY + "false" , out.toString());
    }

    @Test
    void testWriteBooleanTrue() {
        ow.write("key" , true);
        assertEquals(KEY + "true" , out.toString());
    }

    @Test
    void testWriteObjBooleanFalse() {
        ow.write("key" , (Boolean) false);
        assertEquals(KEY + "false" , out.toString());
    }

    @Test
    void testWriteObjBooleanTrue() {
        ow.write("key" , (Boolean) true);
        assertEquals(KEY + "true" , out.toString());
    }

    @Test
    void testWriteObjBooleanNull() {
        ow.write("key" , (Boolean) null);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteBigDecimal() {
        ow.write("key" , BigDecimal.TEN);
        assertEquals(KEY + BigDecimal.TEN, out.toString());
    }

    @Test
    void testWriteBigInteger() {
        ow.write("key" , BigInteger.TEN);
        assertEquals(KEY + BigInteger.TEN, out.toString());
    }

    @Test
    void testWriteBigDecimalNull() {
        ow.write("key" , (BigDecimal) null);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteBigIntegerNull() {
        ow.write("key" , (BigInteger) null);
        assertEquals(KEY + "null" , out.toString());
    }


    @Test
    void testWriteChar() {
        ow.write("key" , 'a');
        assertEquals(KEY + "\"a\"" , out.toString());
    }

    @Test
    void testWriteObjChar() {
        ow.write("key" , (Character) 'a');
        assertEquals(KEY + "\"a\"" , out.toString());
    }

    @Test
    void testWriteObjCharNull() {
        ow.write("key" , (Character) null);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteCharEscape() {
        ow.write("key" , '"');
        assertEquals(KEY + "\"\\\"\"" , out.toString());
    }

    @Test
    void testWriteStringNull() {
        ow.write("key" , (String) null);
        assertEquals(KEY + "null" , out.toString());
    }

    @Test
    void testWriteEmptyString() {
        ow.write("key" , "");
        assertEquals(KEY + "\"\"" , out.toString());
    }

    @Test
    void testWriteString() {
        ow.write("key" , "abc");
        assertEquals(KEY + "\"abc\"" , out.toString());
    }

}