package guru.bug.austras.convert.json.writer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

class JsonValueWriterImpl implements JsonValueWriter {
    private final JsonTokenWriter tokenWriter;

    JsonValueWriterImpl(JsonTokenWriter tokenWriter) {
        this.tokenWriter = tokenWriter;
    }

    @Override
    public void write(boolean value) {
        tokenWriter.write(value);
    }

    @Override
    public void write(Boolean value) {
        if (value == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.write(value);
        }
    }

    @Override
    public void write(byte value) {
        tokenWriter.write(value);
    }

    @Override
    public void write(Byte value) {
        if (value == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.write(value);
        }
    }

    @Override
    public void write(char value) {
        tokenWriter.write(value);
    }

    @Override
    public void write(Character value) {
        if (value == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.write(value);
        }
    }

    @Override
    public void write(double value) {
        tokenWriter.write(value);
    }

    @Override
    public void write(Double value) {
        if (value == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.write(value);
        }
    }

    @Override
    public void write(float value) {
        tokenWriter.write(value);
    }

    @Override
    public void write(Float value) {
        if (value == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.write(value);
        }
    }

    @Override
    public void write(int value) {
        tokenWriter.write(value);
    }

    @Override
    public void write(Integer value) {
        if (value == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.write(value);
        }
    }

    @Override
    public void write(long value) {
        tokenWriter.write(value);
    }

    @Override
    public void write(Long value) {
        if (value == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.write(value);
        }
    }

    @Override
    public void write(short value) {
        tokenWriter.write(value);
    }

    @Override
    public void write(Short value) {
        if (value == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.write(value);
        }
    }

    @Override
    public void write(BigDecimal value) {
        if (value == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.write(value);
        }
    }

    @Override
    public void write(BigInteger value) {
        if (value == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.write(value);
        }
    }

    @Override
    public void write(String value) {
        if (value == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.write(value);
        }
    }

    @Override
    public void writeRaw(String value) {
        if (value != null) {
            tokenWriter.writeRaw(value);
        }
    }

    @Override
    public void writeNull() {
        tokenWriter.writeNull();
    }

    @Override
    public void write(boolean value, JsonBooleanSerializer valueWriterConsumer) {
        valueWriterConsumer.toJson(value, this);
    }

    @Override
    public void write(byte value, JsonByteSerializer valueWriterConsumer) {
        valueWriterConsumer.toJson(value, this);
    }

    @Override
    public void write(char value, JsonCharSerializer valueWriterConsumer) {
        valueWriterConsumer.toJson(value, this);
    }

    @Override
    public void write(double value, JsonDoubleSerializer valueWriterConsumer) {
        valueWriterConsumer.toJson(value, this);
    }

    @Override
    public void write(float value, JsonFloatSerializer valueWriterConsumer) {
        valueWriterConsumer.toJson(value, this);
    }

    @Override
    public void write(int value, JsonIntSerializer valueWriterConsumer) {
        valueWriterConsumer.toJson(value, this);
    }

    @Override
    public void write(long value, JsonLongSerializer valueWriterConsumer) {
        valueWriterConsumer.toJson(value, this);
    }

    @Override
    public void write(short value, JsonShortSerializer valueWriterConsumer) {
        valueWriterConsumer.toJson(value, this);
    }

    @Override
    public <T> void write(T obj, JsonSerializer<T> valueWriterConsumer) {
        valueWriterConsumer.toJson(obj, this);
    }

    @Override
    public void writeArray(boolean[] array, JsonBooleanSerializer valueWriterConsumer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (boolean value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                valueWriterConsumer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public void writeArray(byte[] array, JsonByteSerializer valueWriterConsumer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (byte value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                valueWriterConsumer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public void writeArray(char[] array, JsonCharSerializer valueWriterConsumer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (char value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                valueWriterConsumer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public void writeArray(double[] array, JsonDoubleSerializer valueWriterConsumer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (double value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                valueWriterConsumer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public void writeArray(float[] array, JsonFloatSerializer valueWriterConsumer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (float value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                valueWriterConsumer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public void writeArray(int[] array, JsonIntSerializer valueWriterConsumer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (int value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                valueWriterConsumer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public void writeArray(long[] array, JsonLongSerializer valueWriterConsumer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (long value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                valueWriterConsumer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public void writeArray(short[] array, JsonShortSerializer valueWriterConsumer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (short value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                valueWriterConsumer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }


    @Override
    public <T> void writeObject(T object, JsonObjectSerializer<T> objectWriterConsumer) {
        if (object == null) {
            tokenWriter.writeNull();
        } else {
            var objWriter = new JsonObjectWriterImpl(tokenWriter, this);
            objWriter.writeBegin();
            objectWriterConsumer.toJson(object, objWriter);
            objWriter.writeEnd();
        }
    }

    @Override
    public <T> void writeObjectArray(T[] array, JsonObjectSerializer<T> objectWriterConsumer) {
        writeArray(array, (obj, writer) -> writer.writeObject(obj, objectWriterConsumer));
    }

    @Override
    public <T> void writeObjectArray(Stream<T> stream, JsonObjectSerializer<T> objectWriterConsumer) {
        writeArray(stream, (obj, writer) -> writer.writeObject(obj, objectWriterConsumer));
    }

    @Override
    public <T> void writeObjectArray(Iterable<T> iterable, JsonObjectSerializer<T> objectWriterConsumer) {
        writeArray(iterable, (obj, writer) -> writer.writeObject(obj, objectWriterConsumer));
    }

    @Override
    public <T> void writeObjectArray(Iterator<T> iterator, JsonObjectSerializer<T> objectWriterConsumer) {
        writeArray(iterator, (obj, writer) -> writer.writeObject(obj, objectWriterConsumer));
    }

    @Override
    public <T> void writeArray(T[] array, JsonSerializer<T> valueWriterConsumer) {
        writeArray(array, valueWriterConsumer, itemConsumer -> {
            for (T obj : array) {
                itemConsumer.accept(obj);
            }
        });
    }

    @Override
    public <T> void writeArray(Stream<T> stream, JsonSerializer<T> valueWriterConsumer) {
        writeArray(stream == null ? null : stream.iterator(), valueWriterConsumer);
    }

    @Override
    public <T> void writeArray(Iterable<T> iterable, JsonSerializer<T> valueWriterConsumer) {
        writeArray(iterable == null ? null : iterable.iterator(), valueWriterConsumer);
    }

    @Override
    public <T> void writeArray(Iterator<T> iterator, JsonSerializer<T> valueWriterConsumer) {
        writeArray(iterator, valueWriterConsumer, itemConsumer -> {
            while (iterator.hasNext()) {
                itemConsumer.accept(iterator.next());
            }
        });
    }

    private <T> void writeArray(Object collectionObject, JsonSerializer<T> valueWriterConsumer, Consumer<Consumer<T>> consumer) {
        if (collectionObject == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            consumer.accept(new Consumer<>() {
                boolean hasItems = false;

                @Override
                public void accept(T value) {
                    if (hasItems) {
                        tokenWriter.writeComma();
                    }
                    if (value == null) {
                        tokenWriter.writeNull();
                    } else {
                        valueWriterConsumer.toJson(value, JsonValueWriterImpl.this);
                    }
                    hasItems = true;
                }
            });
            tokenWriter.writeEndArray();
        }
    }
}
