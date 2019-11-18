package guru.bug.austras.convert.json.writer;


import guru.bug.austras.convert.json.utils.JsonWritingException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

class JsonObjectWriterImpl implements JsonObjectWriter {
    private final JsonTokenWriter tokenWriter;
    private final JsonValueWriter valueWriter;
    private final Set<String> usedKeys = new HashSet<>();
    private boolean alreadyHasMembers = false;

    JsonObjectWriterImpl(JsonTokenWriter tokenWriter, JsonValueWriter valueWriter) {
        this.tokenWriter = requireNonNull(tokenWriter);
        this.valueWriter = requireNonNull(valueWriter);
    }

    private void nextKey(String key) {
        requireNonNull(key);
        if (!usedKeys.add(key)) {
            throw new JsonWritingException("duplicate key " + key);
        }
        if (alreadyHasMembers) {
            tokenWriter.writeComma();
        }
        alreadyHasMembers = true;
        tokenWriter.write(key);
        tokenWriter.writeColon();
    }

    void writeBegin() {
        tokenWriter.writeBeginObject();
    }

    void writeEnd() {
        tokenWriter.writeEndObject();
    }

    private void write(String key, Runnable action) {
        nextKey(key);
        action.run();
    }

    @Override
    public <T> void write(String key, T value, JsonSerializer<T> valueWriterConsumer) {
        write(key, () -> valueWriter.write(value, valueWriterConsumer));
    }

    @Override
    public void writeArray(String key, boolean[] array, JsonBooleanSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.writeArray(array, valueWriterConsumer));
    }

    @Override
    public void writeArray(String key, byte[] array, JsonByteSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.writeArray(array, valueWriterConsumer));
    }

    @Override
    public void writeArray(String key, char[] array, JsonCharSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.writeArray(array, valueWriterConsumer));
    }

    @Override
    public void writeArray(String key, double[] array, JsonDoubleSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.writeArray(array, valueWriterConsumer));
    }

    @Override
    public void writeArray(String key, float[] array, JsonFloatSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.writeArray(array, valueWriterConsumer));
    }

    @Override
    public void writeArray(String key, int[] array, JsonIntSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.writeArray(array, valueWriterConsumer));
    }

    @Override
    public void writeArray(String key, long[] array, JsonLongSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.writeArray(array, valueWriterConsumer));
    }

    @Override
    public void writeArray(String key, short[] array, JsonShortSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.writeArray(array, valueWriterConsumer));
    }

    @Override
    public <T> void writeObject(String key, T object, JsonObjectSerializer<T> objectWriterConsumer) {
        write(key, () -> valueWriter.writeObject(object, objectWriterConsumer));
    }

    @Override
    public <T> void writeObjectArray(String key, T[] array, JsonObjectSerializer<T> objectWriterConsumer) {
        write(key, () -> valueWriter.writeObjectArray(array, objectWriterConsumer));
    }

    @Override
    public <T> void writeObjectArray(String key, Stream<T> stream, JsonObjectSerializer<T> objectWriterConsumer) {
        write(key, () -> valueWriter.writeObjectArray(stream, objectWriterConsumer));
    }

    @Override
    public <T> void writeObjectArray(String key, Iterable<T> iterable, JsonObjectSerializer<T> objectWriterConsumer) {
        write(key, () -> valueWriter.writeObjectArray(iterable, objectWriterConsumer));
    }

    @Override
    public <T> void writeObjectArray(String key, Iterator<T> iterator, JsonObjectSerializer<T> objectWriterConsumer) {
        write(key, () -> valueWriter.writeObjectArray(iterator, objectWriterConsumer));
    }

    @Override
    public <T> void writeArray(String key, T[] array, JsonSerializer<T> valueWriterConsumer) {
        write(key, () -> valueWriter.writeArray(array, valueWriterConsumer));
    }

    @Override
    public <T> void writeArray(String key, Stream<T> stream, JsonSerializer<T> valueWriterConsumer) {
        write(key, () -> valueWriter.writeArray(stream, valueWriterConsumer));
    }

    @Override
    public <T> void writeArray(String key, Iterable<T> iterable, JsonSerializer<T> valueWriterConsumer) {
        write(key, () -> valueWriter.writeArray(iterable, valueWriterConsumer));
    }

    @Override
    public <T> void writeArray(String key, Iterator<T> iterator, JsonSerializer<T> valueWriterConsumer) {
        write(key, () -> valueWriter.writeArray(iterator, valueWriterConsumer));
    }

    @Override
    public void write(String key, byte value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, Byte value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, short value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, Short value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, int value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, Integer value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, long value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, Long value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, float value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, Float value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, double value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, Double value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, boolean value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, Boolean value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, char value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, Character value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, String value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, BigDecimal value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void write(String key, BigInteger value) {
        write(key, () -> valueWriter.write(value));
    }

    @Override
    public void writeNull(String key) {
        write(key, valueWriter::writeNull);
    }

    @Override
    public void write(String key, boolean value, JsonBooleanSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.write(value, valueWriterConsumer));
    }

    @Override
    public void write(String key, byte value, JsonByteSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.write(value, valueWriterConsumer));
    }

    @Override
    public void write(String key, char value, JsonCharSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.write(value, valueWriterConsumer));
    }

    @Override
    public void write(String key, double value, JsonDoubleSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.write(value, valueWriterConsumer));
    }

    @Override
    public void write(String key, float value, JsonFloatSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.write(value, valueWriterConsumer));
    }

    @Override
    public void write(String key, int value, JsonIntSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.write(value, valueWriterConsumer));
    }

    @Override
    public void write(String key, long value, JsonLongSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.write(value, valueWriterConsumer));
    }

    @Override
    public void write(String key, short value, JsonShortSerializer valueWriterConsumer) {
        write(key, () -> valueWriter.write(value, valueWriterConsumer));
    }

}
