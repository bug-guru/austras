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
    private final Set<String> usedKeys = new HashSet<>();
    private boolean alreadyHasMembers = false;

    JsonObjectWriterImpl(JsonTokenWriter tokenWriter) {
        this.tokenWriter = requireNonNull(tokenWriter);
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
        write(key, () -> {
            if (value == null) {
                tokenWriter.writeNull();
            } else {
                // TODO think of JsonValueWriter pool
                valueWriterConsumer.toJson(value, new JsonValueWriterImpl(tokenWriter));
            }
        });
    }

    @Override
    public void writeArray(String key, boolean[] array, JsonBooleanSerializer valueWriterConsumer) {

    }

    @Override
    public void writeArray(String key, byte[] array, JsonByteSerializer valueWriterConsumer) {

    }

    @Override
    public void writeArray(String key, char[] array, JsonCharSerializer valueWriterConsumer) {

    }

    @Override
    public void writeArray(String key, double[] array, JsonDoubleSerializer valueWriterConsumer) {

    }

    @Override
    public void writeArray(String key, float[] array, JsonFloatSerializer valueWriterConsumer) {

    }

    @Override
    public void writeArray(String key, int[] array, JsonIntSerializer valueWriterConsumer) {

    }

    @Override
    public void writeArray(String key, long[] array, JsonLongSerializer valueWriterConsumer) {

    }

    @Override
    public void writeArray(String key, short[] array, JsonShortSerializer valueWriterConsumer) {

    }

    @Override
    public <T> void writeObject(String key, T object, JsonObjectSerializer<T> objectWriterConsumer) {
        write(key, () -> {
            if (object == null) {
                tokenWriter.writeNull();
            } else {
                var valueWriter = new JsonValueWriterImpl(tokenWriter);
                valueWriter.writeObject(object, objectWriterConsumer);
            }
        });
    }

    @Override
    public <T> void writeObjectArray(String key, T[] array, JsonObjectSerializer<T> objectWriterConsumer) {
        writeArray(key, array, (obj, writer) -> writer.writeObject(obj, objectWriterConsumer));
    }

    @Override
    public <T> void writeObjectArray(String key, Stream<T> stream, JsonObjectSerializer<T> objectWriterConsumer) {
        writeArray(key, stream, (obj, writer) -> writer.writeObject(obj, objectWriterConsumer));
    }

    @Override
    public <T> void writeObjectArray(String key, Iterable<T> iterable, JsonObjectSerializer<T> objectWriterConsumer) {
        writeArray(key, iterable, (obj, writer) -> writer.writeObject(obj, objectWriterConsumer));
    }

    @Override
    public <T> void writeObjectArray(String key, Iterator<T> iterator, JsonObjectSerializer<T> objectWriterConsumer) {
        writeArray(key, iterator, (obj, writer) -> writer.writeObject(obj, objectWriterConsumer));
    }

    @Override
    public <T> void writeArray(String key, T[] array, JsonSerializer<T> valueWriterConsumer) {
        write(key, () -> {
            if (array == null) {
                tokenWriter.writeNull();
            } else {
                var valueWriter = new JsonValueWriterImpl(tokenWriter);
                valueWriter.writeArray(array, valueWriterConsumer);
            }
        });
    }

    @Override
    public <T> void writeArray(String key, Stream<T> stream, JsonSerializer<T> valueWriterConsumer) {
        write(key, () -> {
            if (stream == null) {
                tokenWriter.writeNull();
            } else {
                var valueWriter = new JsonValueWriterImpl(tokenWriter);
                valueWriter.writeArray(stream, valueWriterConsumer);
            }
        });
    }

    @Override
    public <T> void writeArray(String key, Iterable<T> iterable, JsonSerializer<T> valueWriterConsumer) {
        write(key, () -> {
            if (iterable == null) {
                tokenWriter.writeNull();
            } else {
                var valueWriter = new JsonValueWriterImpl(tokenWriter);
                valueWriter.writeArray(iterable, valueWriterConsumer);
            }
        });
    }

    @Override
    public <T> void writeArray(String key, Iterator<T> iterator, JsonSerializer<T> valueWriterConsumer) {
        write(key, () -> {
            if (iterator == null) {
                tokenWriter.writeNull();
            } else {
                var valueWriter = new JsonValueWriterImpl(tokenWriter);
                valueWriter.writeArray(iterator, valueWriterConsumer);
            }
        });
    }

    @Override
    public void write(String key, byte value) {
        write(key, () -> tokenWriter.write(value));
    }

    @Override
    public void write(String key, Byte value) {
        write(key, () -> {
            if (value == null) {
                tokenWriter.writeNull();
            } else {
                tokenWriter.write(value);
            }
        });
    }

    @Override
    public void write(String key, short value) {
        write(key, () -> tokenWriter.write(value));
    }

    @Override
    public void write(String key, Short value) {
        write(key, () -> {
            if (value == null) {
                tokenWriter.writeNull();
            } else {
                tokenWriter.write(value);
            }
        });
    }

    @Override
    public void write(String key, int value) {
        write(key, () -> tokenWriter.write(value));
    }

    @Override
    public void write(String key, Integer value) {
        write(key, () -> {
            if (value == null) {
                tokenWriter.writeNull();
            } else {
                tokenWriter.write(value);
            }
        });
    }

    @Override
    public void write(String key, long value) {
        write(key, () -> tokenWriter.write(value));
    }

    @Override
    public void write(String key, Long value) {
        write(key, () -> {
            if (value == null) {
                tokenWriter.writeNull();
            } else {
                tokenWriter.write(value);
            }
        });
    }

    @Override
    public void write(String key, float value) {
        write(key, () -> tokenWriter.write(value));
    }

    @Override
    public void write(String key, Float value) {
        write(key, () -> {
            if (value == null) {
                tokenWriter.writeNull();
            } else {
                tokenWriter.write(value);
            }
        });
    }

    @Override
    public void write(String key, double value) {
        write(key, () -> tokenWriter.write(value));
    }

    @Override
    public void write(String key, Double value) {
        write(key, () -> {
            if (value == null) {
                tokenWriter.writeNull();
            } else {
                tokenWriter.write(value);
            }
        });
    }

    @Override
    public void write(String key, boolean value) {
        write(key, () -> tokenWriter.write(value));
    }

    @Override
    public void write(String key, Boolean value) {
        write(key, () -> {
            if (value == null) {
                tokenWriter.writeNull();
            } else {
                tokenWriter.write(value);
            }
        });
    }

    @Override
    public void write(String key, char value) {
        write(key, () -> tokenWriter.write(value));
    }

    @Override
    public void write(String key, Character value) {
        write(key, () -> {
            if (value == null) {
                tokenWriter.writeNull();
            } else {
                tokenWriter.write(value);
            }
        });
    }

    @Override
    public void write(String key, String value) {
        write(key, () -> {
            if (value == null) {
                tokenWriter.writeNull();
            } else {
                tokenWriter.write(value);
            }
        });
    }

    @Override
    public void write(String key, BigDecimal value) {
        write(key, () -> {
            if (value == null) {
                tokenWriter.writeNull();
            } else {
                tokenWriter.write(value);
            }
        });
    }

    @Override
    public void write(String key, BigInteger value) {
        write(key, () -> {
            if (value == null) {
                tokenWriter.writeNull();
            } else {
                tokenWriter.write(value);
            }
        });
    }

    @Override
    public void writeNull(String key) {
        write(key, tokenWriter::writeNull);
    }

    @Override
    public void write(String key, boolean value, JsonBooleanSerializer valueWriterConsumer) {
        write(key, () -> {
            var valueWriter = new JsonValueWriterImpl(tokenWriter);
            valueWriter.write(value, valueWriterConsumer);
        });
    }

    @Override
    public void write(String key, byte value, JsonByteSerializer valueWriterConsumer) {

    }

    @Override
    public void write(String key, char value, JsonCharSerializer valueWriterConsumer) {

    }

    @Override
    public void write(String key, double value, JsonDoubleSerializer valueWriterConsumer) {

    }

    @Override
    public void write(String key, float value, JsonFloatSerializer valueWriterConsumer) {

    }

    @Override
    public void write(String key, int value, JsonIntSerializer valueWriterConsumer) {

    }

    @Override
    public void write(String key, long value, JsonLongSerializer valueWriterConsumer) {

    }

    @Override
    public void write(String key, short value, JsonShortSerializer valueWriterConsumer) {

    }

}
