/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.writer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonValueWriterTest {
    private static final String SAMPLE_ARRAY_JSON = "[{\"key1\":\"aa\",\"key2\":1,\"key3\":\"a\"}," +
            "{\"key1\":\"bb\",\"key2\":2,\"key3\":\"b\"}," +
            "{\"key1\":\"cc\",\"key2\":3,\"key3\":\"c\"}," +
            "null," +
            "{\"key1\":\"ee\",\"key2\":5,\"key3\":\"e\"}]";
    private static final SampleObject[] SAMPLE_ARRAY = new SampleObject[]{
            new SampleObject("aa", 1, 'a'),
            new SampleObject("bb", 2, 'b'),
            new SampleObject("cc", 3, 'c'),
            null,
            new SampleObject("ee", 5, 'e'),
    };
    private JsonValueWriter jsonValueWriter;
    private StringWriter result;

    @BeforeEach
    void initEach() {
        result = new StringWriter();
        jsonValueWriter = JsonValueWriter.newInstance(result);
    }

    @Test
    void testWriteNull() {
        jsonValueWriter.writeNull();
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteByteMin() {
        jsonValueWriter.writeByte(Byte.MIN_VALUE);
        assertEquals(String.valueOf(Byte.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteByteMax() {
        jsonValueWriter.writeByte(Byte.MAX_VALUE);
        assertEquals(String.valueOf(Byte.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjByteMin() {
        jsonValueWriter.writeByte((Byte) Byte.MIN_VALUE);
        assertEquals(String.valueOf(Byte.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteObjByteMax() {
        jsonValueWriter.writeByte((Byte) Byte.MAX_VALUE);
        assertEquals(String.valueOf(Byte.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjByteNull() {
        jsonValueWriter.writeByte(null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteShortMin() {
        jsonValueWriter.writeShort(Short.MIN_VALUE);
        assertEquals(String.valueOf(Short.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteShortMax() {
        jsonValueWriter.writeShort(Short.MAX_VALUE);
        assertEquals(String.valueOf(Short.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjShortMin() {
        jsonValueWriter.writeShort((Short) Short.MIN_VALUE);
        assertEquals(String.valueOf(Short.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteObjShortMax() {
        jsonValueWriter.writeShort((Short) Short.MAX_VALUE);
        assertEquals(String.valueOf(Short.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjShortNull() {
        jsonValueWriter.writeShort(null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteIntMin() {
        jsonValueWriter.writeInteger(Integer.MIN_VALUE);
        assertEquals(String.valueOf(Integer.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteIntMax() {
        jsonValueWriter.writeInteger(Integer.MAX_VALUE);
        assertEquals(String.valueOf(Integer.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjIntMin() {
        jsonValueWriter.writeInteger((Integer) Integer.MIN_VALUE);
        assertEquals(String.valueOf(Integer.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteObjIntMax() {
        jsonValueWriter.writeInteger((Integer) Integer.MAX_VALUE);
        assertEquals(String.valueOf(Integer.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjIntNull() {
        jsonValueWriter.writeInteger(null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteLongMin() {
        jsonValueWriter.writeLong(Long.MIN_VALUE);
        assertEquals(String.valueOf(Long.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteLongMax() {
        jsonValueWriter.writeLong(Long.MAX_VALUE);
        assertEquals(String.valueOf(Long.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjLongMin() {
        jsonValueWriter.writeLong((Long) Long.MIN_VALUE);
        assertEquals(String.valueOf(Long.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteObjLongMax() {
        jsonValueWriter.writeLong((Long) Long.MAX_VALUE);
        assertEquals(String.valueOf(Long.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjLongNull() {
        jsonValueWriter.writeLong(null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteFloatNegativeInfinitive() {
        jsonValueWriter.writeFloat(Float.NEGATIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteFloatPositiveInfinitive() {
        jsonValueWriter.writeFloat(Float.POSITIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteFloatNaN() {
        jsonValueWriter.writeFloat(Float.NaN);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteFloatMin() {
        jsonValueWriter.writeFloat(Float.MIN_VALUE);
        assertEquals(String.valueOf(Float.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteFloatMax() {
        jsonValueWriter.writeFloat(Float.MAX_VALUE);
        assertEquals(String.valueOf(Float.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjFloatNegativeInfinitive() {
        jsonValueWriter.writeFloat((Float) Float.NEGATIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteObjFloatPositiveInfinitive() {
        jsonValueWriter.writeFloat((Float) Float.POSITIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteObjFloatNaN() {
        jsonValueWriter.writeFloat((Float) Float.NaN);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteObjFloatMin() {
        jsonValueWriter.writeFloat((Float) Float.MIN_VALUE);
        assertEquals(String.valueOf(Float.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteObjFloatMax() {
        jsonValueWriter.writeFloat((Float) Float.MAX_VALUE);
        assertEquals(String.valueOf(Float.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjFloatNull() {
        jsonValueWriter.writeFloat(null);
        assertEquals("null" , result.toString());
    }


    @Test
    void testWriteDoubleNegativeInfinitive() {
        jsonValueWriter.writeDouble(Double.NEGATIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteDoublePositiveInfinitive() {
        jsonValueWriter.writeDouble(Double.POSITIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteDoubleNaN() {
        jsonValueWriter.writeDouble(Double.NaN);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteDoubleMin() {
        jsonValueWriter.writeDouble(Double.MIN_VALUE);
        assertEquals(String.valueOf(Double.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteDoubleMax() {
        jsonValueWriter.writeDouble(Double.MAX_VALUE);
        assertEquals(String.valueOf(Double.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjDoubleNegativeInfinitive() {
        jsonValueWriter.writeDouble((Double) Double.NEGATIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteObjDoublePositiveInfinitive() {
        jsonValueWriter.writeDouble((Double) Double.POSITIVE_INFINITY);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteObjDoubleNaN() {
        jsonValueWriter.writeDouble((Double) Double.NaN);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteObjDoubleMin() {
        jsonValueWriter.writeDouble((Double) Double.MIN_VALUE);
        assertEquals(String.valueOf(Double.MIN_VALUE), result.toString());
    }

    @Test
    void testWriteObjDoubleMax() {
        jsonValueWriter.writeDouble((Double) Double.MAX_VALUE);
        assertEquals(String.valueOf(Double.MAX_VALUE), result.toString());
    }

    @Test
    void testWriteObjDoubleNull() {
        jsonValueWriter.writeDouble(null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteBooleanFalse() {
        jsonValueWriter.writeBoolean(false);
        assertEquals("false" , result.toString());
    }

    @Test
    void testWriteBooleanTrue() {
        jsonValueWriter.writeBoolean(true);
        assertEquals("true" , result.toString());
    }

    @Test
    void testWriteObjBooleanFalse() {
        jsonValueWriter.writeBoolean((Boolean) false);
        assertEquals("false" , result.toString());
    }

    @Test
    void testWriteObjBooleanTrue() {
        jsonValueWriter.writeBoolean((Boolean) true);
        assertEquals("true" , result.toString());
    }

    @Test
    void testWriteObjBooleanNull() {
        jsonValueWriter.writeBoolean(null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteBigDecimal() {
        jsonValueWriter.writeBigDecimal(BigDecimal.TEN);
        assertEquals(String.valueOf(BigDecimal.TEN), result.toString());
    }

    @Test
    void testWriteBigInteger() {
        jsonValueWriter.writeBigInteger(BigInteger.TEN);
        assertEquals(String.valueOf(BigInteger.TEN), result.toString());
    }

    @Test
    void testWriteBigDecimalNull() {
        jsonValueWriter.writeBigDecimal(null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteBigIntegerNull() {
        jsonValueWriter.writeBigInteger(null);
        assertEquals("null" , result.toString());
    }


    @Test
    void testWriteChar() {
        jsonValueWriter.writeCharacter('a');
        assertEquals("\"a\"" , result.toString());
    }

    @Test
    void testWriteObjChar() {
        jsonValueWriter.writeCharacter((Character) 'a');
        assertEquals("\"a\"" , result.toString());
    }

    @Test
    void testWriteObjCharNull() {
        jsonValueWriter.writeCharacter(null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteCharEscape() {
        jsonValueWriter.writeCharacter('"');
        assertEquals("\"\\\"\"" , result.toString());
    }

    @Test
    void testWriteStringNull() {
        jsonValueWriter.writeString(null);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteEmptyString() {
        jsonValueWriter.writeString("");
        assertEquals("\"\"" , result.toString());
    }

    @Test
    void testWriteString() {
        jsonValueWriter.writeString("abc");
        assertEquals("\"abc\"" , result.toString());
    }


    @Test
    void testWriteSimpleObject() {
        jsonValueWriter.writeObject("" , (object, writer) -> {
            writer.writeString("key1" , "string" , this::writeStr);
            writer.writeInteger("key2" , 1234, this::writeInt);
            writer.writeCharacter("key3" , 'x', this::writeChar);
        });
        assertEquals("{\"key1\":\"string\",\"key2\":1234,\"key3\":\"x\"}" , result.toString());
    }

    private void writeStr(String str, JsonValueWriter valueWriter) {
        valueWriter.writeString(str);
    }

    private void writeInt(int i, JsonValueWriter valueWriter) {
        valueWriter.writeInteger(i);
    }

    private void writeChar(char ch, JsonValueWriter valueWriter) {
        valueWriter.writeCharacter(ch);
    }

    private void writeInts(List<Integer> integers, JsonValueWriter valueWriter) {
        valueWriter.writeIntegerArray(integers, this::writeInt);
    }

    private void writeStrings(List<String> strings, JsonValueWriter valueWriter) {
        valueWriter.writeStringArray(strings, this::writeStr);
    }


    private void writeSampleObject(SampleObject obj, JsonValueWriter valueWriter) {
        valueWriter.writeObject(obj, (o, ow) -> {
            ow.writeString("key1" , o.getKey1(), this::writeStr);
            ow.writeInteger("key2" , o.getKey2(), this::writeInt);
            ow.writeCharacter("key3" , o.getKey3(), this::writeChar);
        });
    }

    private void writeSampleObject(List<SampleObject> sampleObjects, JsonValueWriter valueWriter) {
        valueWriter.writeObjectArray(sampleObjects, this::writeSampleObject);
    }

    @Test
    void testWriteComplexObject() {
        var sample = new SampleObject();
        jsonValueWriter.writeObject("" , (object, writer) -> {
            writer.writeInteger("a" , 5, this::writeInt);
            writer.writeObject("obj" , sample, this::writeSampleObject);
            writer.writeValue("ints" , List.of(1, 2, 3), this::writeInts);
            writer.writeValue("objects" , List.of(sample, sample), this::writeSampleObject);
        });
        String jsonSample = "{\"key1\":\"string\",\"key2\":1234,\"key3\":\"x\"}";
        assertEquals("{\"a\":5,\"obj\":" + jsonSample + ",\"ints\":[1,2,3],\"objects\":[" + jsonSample + "," + jsonSample + "]}" , result.toString());
    }

    @Test
    void testWriteNullArray() {
        jsonValueWriter.writeStringArray((String[]) null, this::writeStr);
        assertEquals("null" , result.toString());
    }

    @Test
    void testWriteArrayWithNulls() {
        jsonValueWriter.writeStringArray(new String[]{null, "a" , null, "b" , null}, this::writeStr);
        assertEquals("[null,\"a\",null,\"b\",null]" , result.toString());
    }

    @Test
    void testWriteArrayAsList() {
        jsonValueWriter.writeStringArray(List.of(), this::writeStr);
        assertEquals("[]" , result.toString());
    }

    private void writeSampleObject(SampleObject obj, JsonObjectWriter writer) {
        writer.writeString("key1" , obj.getKey1());
        writer.writeInteger("key2" , obj.getKey2());
        writer.writeCharacter("key3" , obj.getKey3());
    }

    @Test
    void writeObjectArray_Array() {
        jsonValueWriter.writeObjectArray(SAMPLE_ARRAY, this::writeSampleObject);
        assertEquals(SAMPLE_ARRAY_JSON, result.toString());
    }

    @Test
    void writeObjectArray_List() {
        jsonValueWriter.writeObjectArray(Arrays.asList(SAMPLE_ARRAY), this::writeSampleObject);
        assertEquals(SAMPLE_ARRAY_JSON, result.toString());
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
