package guru.bug.austras.convert.json.writer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.stream.Stream;

public interface JsonValueWriter {
    void write(boolean value);

    void write(Boolean value);

    void write(byte value);

    void write(Byte value);

    void write(char value);

    void write(Character value);

    void write(double value);

    void write(Double value);

    void write(float value);

    void write(Float value);

    void write(int value);

    void write(Integer value);

    void write(long value);

    void write(Long value);

    void write(short value);

    void write(Short value);

    void write(BigDecimal value);

    void write(BigInteger value);

    void write(String value);

    void writeRaw(String value);

    void writeNull();

    void write(boolean value, JsonBooleanSerializer valueWriterConsumer);

    void write(byte value, JsonByteSerializer valueWriterConsumer);

    void write(char value, JsonCharSerializer valueWriterConsumer);

    void write(double value, JsonDoubleSerializer valueWriterConsumer);

    void write(float value, JsonFloatSerializer valueWriterConsumer);

    void write(int value, JsonIntSerializer valueWriterConsumer);

    void write(long value, JsonLongSerializer valueWriterConsumer);

    void write(short value, JsonShortSerializer valueWriterConsumer);

    <T> void write(T value, JsonSerializer<T> valueWriterConsumer);

    void writeArray(boolean[] array, JsonBooleanSerializer valueWriterConsumer);

    void writeArray(byte[] array, JsonByteSerializer valueWriterConsumer);

    void writeArray(char[] array, JsonCharSerializer valueWriterConsumer);

    void writeArray(double[] array, JsonDoubleSerializer valueWriterConsumer);

    void writeArray(float[] array, JsonFloatSerializer valueWriterConsumer);

    void writeArray(int[] array, JsonIntSerializer valueWriterConsumer);

    void writeArray(long[] array, JsonLongSerializer valueWriterConsumer);

    void writeArray(short[] array, JsonShortSerializer valueWriterConsumer);

    <T> void writeArray(T[] array, JsonSerializer<T> valueWriterConsumer);

    <T> void writeArray(Stream<T> stream, JsonSerializer<T> valueWriterConsumer);

    <T> void writeArray(Iterable<T> iterable, JsonSerializer<T> valueWriterConsumer);

    <T> void writeArray(Iterator<T> iterator, JsonSerializer<T> valueWriterConsumer);

    <T> void writeObject(T object, JsonObjectSerializer<T> objectWriterConsumer);

    <T> void writeObjectArray(T[] array, JsonObjectSerializer<T> objectWriterConsumer);

    <T> void writeObjectArray(Stream<T> stream, JsonObjectSerializer<T> objectWriterConsumer);

    <T> void writeObjectArray(Iterable<T> iterable, JsonObjectSerializer<T> objectWriterConsumer);

    <T> void writeObjectArray(Iterator<T> iterator, JsonObjectSerializer<T> objectWriterConsumer);
}
