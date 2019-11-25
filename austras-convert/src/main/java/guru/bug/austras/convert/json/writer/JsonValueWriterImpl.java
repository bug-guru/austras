package guru.bug.austras.convert.json.writer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

class JsonValueWriterImpl implements JsonValueWriter {
    private final JsonTokenWriter tokenWriter;

    JsonValueWriterImpl(JsonTokenWriter tokenWriter) {
        this.tokenWriter = tokenWriter;
    }

    /* BOOLEAN */

    @Override
    public void writeBoolean(boolean value, JsonBooleanSerializer serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeBoolean(Boolean value, JsonSerializer<Boolean> serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeBooleanArray(boolean[] array, JsonBooleanSerializer serializer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (boolean value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                serializer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public void writeBooleanArray(Boolean[] array, JsonSerializer<Boolean> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeBooleanArray(Collection<Boolean> array, JsonSerializer<Boolean> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeBoolean(boolean value) {
        tokenWriter.writeBoolean(value);
    }

    @Override
    public void writeBoolean(Boolean value) {
        tokenWriter.writeBoolean(value);
    }

    @Override
    public void writeBooleanArray(boolean[] array) {
        writeBooleanArray(array, (value, writer) -> writer.writeBoolean(value));
    }

    @Override
    public void writeBooleanArray(Boolean[] array) {
        writeBooleanArray(array, (value, writer) -> writer.writeBoolean(value));
    }

    @Override
    public void writeBooleanArray(Collection<Boolean> array) {
        writeBooleanArray(array, (value, writer) -> writer.writeBoolean(value));
    }

    /* BYTE */

    @Override
    public void writeByte(byte value, JsonByteSerializer serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeByte(Byte value, JsonSerializer<Byte> serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeByteArray(byte[] array, JsonByteSerializer serializer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (byte value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                serializer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public void writeByteArray(Byte[] array, JsonSerializer<Byte> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeByteArray(Collection<Byte> array, JsonSerializer<Byte> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeByte(byte value) {
        tokenWriter.writeByte(value);
    }

    @Override
    public void writeByte(Byte value) {
        tokenWriter.writeByte(value);
    }

    @Override
    public void writeByteArray(byte[] array) {
        writeByteArray(array, (value, writer) -> writer.writeByte(value));
    }

    @Override
    public void writeByteArray(Byte[] array) {
        writeByteArray(array, (value, writer) -> writer.writeByte(value));
    }

    @Override
    public void writeByteArray(Collection<Byte> array) {
        writeByteArray(array, (value, writer) -> writer.writeByte(value));
    }

    /* SHORT */

    @Override
    public void writeShort(short value, JsonShortSerializer serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeShort(Short value, JsonSerializer<Short> serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeShortArray(short[] array, JsonShortSerializer serializer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (short value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                serializer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public void writeShortArray(Short[] array, JsonSerializer<Short> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeShortArray(Collection<Short> array, JsonSerializer<Short> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeShort(short value) {
        tokenWriter.writeShort(value);
    }

    @Override
    public void writeShort(Short value) {
        tokenWriter.writeShort(value);
    }

    @Override
    public void writeShortArray(short[] array) {
        writeShortArray(array, (value, writer) -> writer.writeShort(value));
    }

    @Override
    public void writeShortArray(Short[] array) {
        writeShortArray(array, (value, writer) -> writer.writeShort(value));
    }

    @Override
    public void writeShortArray(Collection<Short> array) {
        writeShortArray(array, (value, writer) -> writer.writeShort(value));
    }

    /* INTEGER */

    @Override
    public void writeInteger(int value, JsonIntegerSerializer serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeInteger(Integer value, JsonSerializer<Integer> serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeIntegerArray(int[] array, JsonIntegerSerializer serializer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (int value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                serializer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public void writeIntegerArray(Integer[] array, JsonSerializer<Integer> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeIntegerArray(Collection<Integer> array, JsonSerializer<Integer> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeInteger(int value) {
        tokenWriter.writeInteger(value);
    }

    @Override
    public void writeInteger(Integer value) {
        tokenWriter.writeInteger(value);
    }

    @Override
    public void writeIntegerArray(int[] array) {
        writeIntegerArray(array, (value, writer) -> writer.writeInteger(value));
    }

    @Override
    public void writeIntegerArray(Integer[] array) {
        writeIntegerArray(array, (value, writer) -> writer.writeInteger(value));
    }

    @Override
    public void writeIntegerArray(Collection<Integer> array) {
        writeIntegerArray(array, (value, writer) -> writer.writeInteger(value));
    }

    /* LONG */

    @Override
    public void writeLong(long value, JsonLongSerializer serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeLong(Long value, JsonSerializer<Long> serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeLongArray(long[] array, JsonLongSerializer serializer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (long value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                serializer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public void writeLongArray(Long[] array, JsonSerializer<Long> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeLongArray(Collection<Long> array, JsonSerializer<Long> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeLong(long value) {
        tokenWriter.writeLong(value);
    }

    @Override
    public void writeLong(Long value) {
        tokenWriter.writeLong(value);
    }

    @Override
    public void writeLongArray(long[] array) {
        writeLongArray(array, (value, writer) -> writer.writeLong(value));
    }

    @Override
    public void writeLongArray(Long[] array) {
        writeLongArray(array, (value, writer) -> writer.writeLong(value));
    }

    @Override
    public void writeLongArray(Collection<Long> array) {
        writeLongArray(array, (value, writer) -> writer.writeLong(value));
    }

    /* FLOAT */

    @Override
    public void writeFloat(float value, JsonFloatSerializer serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeFloat(Float value, JsonSerializer<Float> serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeFloatArray(float[] array, JsonFloatSerializer serializer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (float value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                serializer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public void writeFloatArray(Float[] array, JsonSerializer<Float> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeFloatArray(Collection<Float> array, JsonSerializer<Float> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeFloat(float value) {
        tokenWriter.writeFloat(value);
    }

    @Override
    public void writeFloat(Float value) {
        tokenWriter.writeFloat(value);
    }

    @Override
    public void writeFloatArray(float[] array) {
        writeFloatArray(array, (value, writer) -> writer.writeFloat(value));
    }

    @Override
    public void writeFloatArray(Float[] array) {
        writeFloatArray(array, (value, writer) -> writer.writeFloat(value));
    }

    @Override
    public void writeFloatArray(Collection<Float> array) {
        writeFloatArray(array, (value, writer) -> writer.writeFloat(value));
    }

    /* DOUBLE */

    @Override
    public void writeDouble(double value, JsonDoubleSerializer serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeDouble(Double value, JsonSerializer<Double> serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeDoubleArray(double[] array, JsonDoubleSerializer serializer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (double value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                serializer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public void writeDoubleArray(Double[] array, JsonSerializer<Double> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeDoubleArray(Collection<Double> array, JsonSerializer<Double> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeDouble(double value) {
        tokenWriter.writeDouble(value);
    }

    @Override
    public void writeDouble(Double value) {
        tokenWriter.writeDouble(value);
    }

    @Override
    public void writeDoubleArray(double[] array) {
        writeDoubleArray(array, (value, writer) -> writer.writeDouble(value));
    }

    @Override
    public void writeDoubleArray(Double[] array) {
        writeDoubleArray(array, (value, writer) -> writer.writeDouble(value));
    }

    @Override
    public void writeDoubleArray(Collection<Double> array) {
        writeDoubleArray(array, (value, writer) -> writer.writeDouble(value));
    }

    /* BIG INTEGER */

    @Override
    public void writeBigInteger(BigInteger value, JsonSerializer<BigInteger> serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeBigIntegerArray(BigInteger[] array, JsonSerializer<BigInteger> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeBigIntegerArray(Collection<BigInteger> array, JsonSerializer<BigInteger> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeBigInteger(BigInteger value) {
        tokenWriter.writeBigInteger(value);
    }

    @Override
    public void writeBigIntegerArray(BigInteger[] array) {
        writeBigIntegerArray(array, (value, writer) -> writer.writeBigInteger(value));
    }

    @Override
    public void writeBigIntegerArray(Collection<BigInteger> array) {
        writeBigIntegerArray(array, (value, writer) -> writer.writeBigInteger(value));
    }

    /* BIG DECIMAL */

    @Override
    public void writeBigDecimal(BigDecimal value, JsonSerializer<BigDecimal> serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeBigDecimalArray(BigDecimal[] array, JsonSerializer<BigDecimal> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeBigDecimalArray(Collection<BigDecimal> array, JsonSerializer<BigDecimal> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeBigDecimal(BigDecimal value) {
        tokenWriter.writeBigDecimal(value);
    }

    @Override
    public void writeBigDecimalArray(BigDecimal[] array) {
        writeBigDecimalArray(array, (value, writer) -> writer.writeBigDecimal(value));
    }

    @Override
    public void writeBigDecimalArray(Collection<BigDecimal> array) {
        writeBigDecimalArray(array, (value, writer) -> writer.writeBigDecimal(value));
    }

    /* STRING */

    @Override
    public void writeString(String value, JsonSerializer<String> serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeStringArray(String[] array, JsonSerializer<String> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeStringArray(Collection<String> array, JsonSerializer<String> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeString(String value) {
        tokenWriter.writeString(value);
    }

    @Override
    public void writeStringArray(String[] array) {
        writeStringArray(array, (value, writer) -> writer.writeString(value));
    }

    @Override
    public void writeStringArray(Collection<String> array) {
        writeStringArray(array, (value, writer) -> writer.writeString(value));
    }

    /* CHARACTER */

    @Override
    public void writeCharacter(char value, JsonCharSerializer serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeCharacter(Character value, JsonSerializer<Character> serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public void writeCharacterArray(char[] array, JsonCharSerializer serializer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (char value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                serializer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public void writeCharacterArray(Character[] array, JsonSerializer<Character> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeCharacterArray(Collection<Character> array, JsonSerializer<Character> serializer) {
        writeValueArray(array, serializer);
    }

    @Override
    public void writeCharacter(char value) {
        tokenWriter.writeCharacter(value);
    }

    @Override
    public void writeCharacter(Character value) {
        tokenWriter.writeCharacter(value);
    }

    @Override
    public void writeCharacterArray(char[] array) {
        writeCharacterArray(array, (value, writer) -> writer.writeCharacter(value));
    }

    @Override
    public void writeCharacterArray(Character[] array) {
        writeCharacterArray(array, (value, writer) -> writer.writeCharacter(value));
    }

    @Override
    public void writeCharacterArray(Collection<Character> array) {
        writeCharacterArray(array, (value, writer) -> writer.writeCharacter(value));
    }

    /* CUSTOM */

    @Override
    public <T> void writeValue(T value, JsonSerializer<T> serializer) {
        serializer.toJson(value, this);
    }

    @Override
    public <T> void writeValueArray(T[] array, JsonSerializer<T> serializer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (T value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                serializer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public <T> void writeValueArray(Collection<T> array, JsonSerializer<T> serializer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (T value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                serializer.toJson(value, this);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    /* OBJECT */

    @Override
    public <T> void writeObject(T value, JsonObjectSerializer<T> serializer) {
        if (value == null) {
            tokenWriter.writeNull();
        } else {
            var objWriter = new JsonObjectWriterImpl(tokenWriter, this);
            tokenWriter.writeBeginObject();
            serializer.toJson(value, objWriter);
            tokenWriter.writeEndObject();
        }
    }

    @Override
    public <T> void writeObjectArray(T[] array, JsonObjectSerializer<T> serializer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (T value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                writeObject(value, serializer);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    @Override
    public <T> void writeObjectArray(Collection<T> array, JsonObjectSerializer<T> serializer) {
        if (array == null) {
            tokenWriter.writeNull();
        } else {
            tokenWriter.writeBeginArray();
            boolean hasItems = false;
            for (T value : array) {
                if (hasItems) {
                    tokenWriter.writeComma();
                }
                writeObject(value, serializer);
                hasItems = true;
            }
            tokenWriter.writeEndArray();
        }
    }

    /* NULL */

    @Override
    public void writeNull() {
        tokenWriter.writeNull();
    }
}
