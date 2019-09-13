package guru.bug.austras.convert.json.writer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.stream.Stream;

public interface JsonObjectWriter {

    void write(String key, byte value);

    void write(String key, Byte value);

    void write(String key, short value);

    void write(String key, Short value);

    void write(String key, int value);

    void write(String key, Integer value);

    void write(String key, long value);

    void write(String key, Long value);

    void write(String key, float value);

    void write(String key, Float value);

    void write(String key, double value);

    void write(String key, Double value);

    void write(String key, boolean value);

    void write(String key, Boolean value);

    void write(String key, char value);

    void write(String key, Character value);

    void write(String key, String value);

    void write(String key, BigDecimal value);

    void write(String key, BigInteger value);

    void writeNull(String key);

    void write(String key, boolean value, JsonBooleanSerializer valueWriterConsumer);

    void write(String key, byte value, JsonByteSerializer valueWriterConsumer);

    void write(String key, char value, JsonCharSerializer valueWriterConsumer);

    void write(String key, double value, JsonDoubleSerializer valueWriterConsumer);

    void write(String key, float value, JsonFloatSerializer valueWriterConsumer);

    void write(String key, int value, JsonIntSerializer valueWriterConsumer);

    void write(String key, long value, JsonLongSerializer valueWriterConsumer);

    void write(String key, short value, JsonShortSerializer valueWriterConsumer);

    <T> void write(String key, T value, JsonSerializer<T> valueWriterConsumer);

    void writeArray(String key, boolean[] array, JsonBooleanSerializer valueWriterConsumer);

    void writeArray(String key, byte[] array, JsonByteSerializer valueWriterConsumer);

    void writeArray(String key, char[] array, JsonCharSerializer valueWriterConsumer);

    void writeArray(String key, double[] array, JsonDoubleSerializer valueWriterConsumer);

    void writeArray(String key, float[] array, JsonFloatSerializer valueWriterConsumer);

    void writeArray(String key, int[] array, JsonIntSerializer valueWriterConsumer);

    void writeArray(String key, long[] array, JsonLongSerializer valueWriterConsumer);

    void writeArray(String key, short[] array, JsonShortSerializer valueWriterConsumer);

    <T> void writeArray(String key, T[] array, JsonSerializer<T> valueWriterConsumer);

    <T> void writeArray(String key, Stream<T> stream, JsonSerializer<T> valueWriterConsumer);

    <T> void writeArray(String key, Iterable<T> stream, JsonSerializer<T> valueWriterConsumer);

    <T> void writeArray(String key, Iterator<T> stream, JsonSerializer<T> valueWriterConsumer);

    <T> void writeObject(String key, T object, JsonObjectSerializer<T> objectWriterConsumer);

    <T> void writeObjectArray(String key, T[] array, JsonObjectSerializer<T> objectWriterConsumer);

    <T> void writeObjectArray(String key, Stream<T> stream, JsonObjectSerializer<T> objectWriterConsumer);

    <T> void writeObjectArray(String key, Iterable<T> iterable, JsonObjectSerializer<T> objectWriterConsumer);

    <T> void writeObjectArray(String key, Iterator<T> iterator, JsonObjectSerializer<T> objectWriterConsumer);

}
