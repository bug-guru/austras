/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.writer;

import guru.bug.austras.json.utils.JsonWritingException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

    /* COMMON */

    private void nextKey(String key) {
        requireNonNull(key);
        if (!usedKeys.add(key)) {
            throw new JsonWritingException("duplicate key " + key);
        }
        if (alreadyHasMembers) {
            tokenWriter.writeComma();
        }
        alreadyHasMembers = true;
        tokenWriter.writeString(key);
        tokenWriter.writeColon();
    }

    private void write(String key, Runnable action) {
        nextKey(key);
        action.run();
    }

    /* BOOLEAN */

    @Override
    public void writeValue(String key, boolean value, JsonBooleanSerializer serializer) {
        writeBoolean(key, value, serializer);
    }

    @Override
    public void writeBoolean(String key, boolean value, JsonBooleanSerializer serializer) {
        write(key, () -> valueWriter.writeBoolean(value, serializer));
    }

    @Override
    public void writeBoolean(String key, Boolean value, JsonSerializer<Boolean> serializer) {
        write(key, () -> valueWriter.writeBoolean(value, serializer));
    }

    @Override
    public void writeBooleanArray(String key, boolean[] array, JsonBooleanSerializer serializer) {
        write(key, () -> valueWriter.writeBooleanArray(array, serializer));
    }

    @Override
    public void writeBooleanArray(String key, Boolean[] array, JsonSerializer<Boolean> serializer) {
        write(key, () -> valueWriter.writeBooleanArray(array, serializer));
    }

    @Override
    public void writeBooleanArray(String key, Collection<Boolean> array, JsonSerializer<Boolean> serializer) {
        write(key, () -> valueWriter.writeBooleanArray(array, serializer));
    }

    @Override
    public void writeBoolean(String key, boolean value) {
        write(key, () -> valueWriter.writeBoolean(value));
    }

    @Override
    public void writeBoolean(String key, Boolean value) {
        write(key, () -> valueWriter.writeBoolean(value));
    }

    @Override
    public void writeBooleanArray(String key, boolean[] array) {
        write(key, () -> valueWriter.writeBooleanArray(array));
    }

    @Override
    public void writeBooleanArray(String key, Boolean[] array) {
        write(key, () -> valueWriter.writeBooleanArray(array));
    }

    @Override
    public void writeBooleanArray(String key, Collection<Boolean> array) {
        write(key, () -> valueWriter.writeBooleanArray(array));
    }

    /* BYTE */

    @Override
    public void writeValue(String key, byte value, JsonByteSerializer serializer) {
        writeByte(key, value, serializer);
    }

    @Override
    public void writeByte(String key, byte value, JsonByteSerializer serializer) {
        write(key, () -> valueWriter.writeByte(value, serializer));
    }

    @Override
    public void writeByte(String key, Byte value, JsonSerializer<Byte> serializer) {
        write(key, () -> valueWriter.writeByte(value, serializer));
    }

    @Override
    public void writeByteArray(String key, byte[] array, JsonByteSerializer serializer) {
        write(key, () -> valueWriter.writeByteArray(array, serializer));
    }

    @Override
    public void writeByteArray(String key, Byte[] array, JsonSerializer<Byte> serializer) {
        write(key, () -> valueWriter.writeByteArray(array, serializer));
    }

    @Override
    public void writeByteArray(String key, Collection<Byte> array, JsonSerializer<Byte> serializer) {
        write(key, () -> valueWriter.writeByteArray(array, serializer));
    }

    @Override
    public void writeByte(String key, byte value) {
        write(key, () -> valueWriter.writeByte(value));
    }

    @Override
    public void writeByte(String key, Byte value) {
        write(key, () -> valueWriter.writeByte(value));
    }

    @Override
    public void writeByteArray(String key, byte[] array) {
        write(key, () -> valueWriter.writeByteArray(array));
    }

    @Override
    public void writeByteArray(String key, Byte[] array) {
        write(key, () -> valueWriter.writeByteArray(array));
    }

    @Override
    public void writeByteArray(String key, Collection<Byte> array) {
        write(key, () -> valueWriter.writeByteArray(array));
    }

    /* SHORT */

    @Override
    public void writeValue(String key, short value, JsonShortSerializer serializer) {
        writeShort(key, value, serializer);
    }

    @Override
    public void writeShort(String key, short value, JsonShortSerializer serializer) {
        write(key, () -> valueWriter.writeShort(value, serializer));
    }

    @Override
    public void writeShort(String key, Short value, JsonSerializer<Short> serializer) {
        write(key, () -> valueWriter.writeShort(value, serializer));
    }

    @Override
    public void writeShortArray(String key, short[] array, JsonShortSerializer serializer) {
        write(key, () -> valueWriter.writeShortArray(array, serializer));
    }

    @Override
    public void writeShortArray(String key, Short[] array, JsonSerializer<Short> serializer) {
        write(key, () -> valueWriter.writeShortArray(array, serializer));
    }

    @Override
    public void writeShortArray(String key, Collection<Short> array, JsonSerializer<Short> serializer) {
        write(key, () -> valueWriter.writeShortArray(array, serializer));
    }

    @Override
    public void writeShort(String key, short value) {
        write(key, () -> valueWriter.writeShort(value));
    }

    @Override
    public void writeShort(String key, Short value) {
        write(key, () -> valueWriter.writeShort(value));
    }

    @Override
    public void writeShortArray(String key, short[] array) {
        write(key, () -> valueWriter.writeShortArray(array));
    }

    @Override
    public void writeShortArray(String key, Short[] array) {
        write(key, () -> valueWriter.writeShortArray(array));
    }

    @Override
    public void writeShortArray(String key, Collection<Short> array) {
        write(key, () -> valueWriter.writeShortArray(array));
    }

    /* INTEGER */

    @Override
    public void writeValue(String key, int value, JsonIntegerSerializer serializer) {
        writeInteger(key, value, serializer);
    }

    @Override
    public void writeInteger(String key, int value, JsonIntegerSerializer serializer) {
        write(key, () -> valueWriter.writeInteger(value, serializer));
    }

    @Override
    public void writeInteger(String key, Integer value, JsonSerializer<Integer> serializer) {
        write(key, () -> valueWriter.writeInteger(value, serializer));
    }

    @Override
    public void writeIntegerArray(String key, int[] array, JsonIntegerSerializer serializer) {
        write(key, () -> valueWriter.writeIntegerArray(array, serializer));
    }

    @Override
    public void writeIntegerArray(String key, Integer[] array, JsonSerializer<Integer> serializer) {
        write(key, () -> valueWriter.writeIntegerArray(array, serializer));
    }

    @Override
    public void writeIntegerArray(String key, Collection<Integer> array, JsonSerializer<Integer> serializer) {
        write(key, () -> valueWriter.writeIntegerArray(array, serializer));
    }

    @Override
    public void writeInteger(String key, int value) {
        write(key, () -> valueWriter.writeInteger(value));
    }

    @Override
    public void writeInteger(String key, Integer value) {
        write(key, () -> valueWriter.writeInteger(value));
    }

    @Override
    public void writeIntegerArray(String key, int[] array) {
        write(key, () -> valueWriter.writeIntegerArray(array));
    }

    @Override
    public void writeIntegerArray(String key, Integer[] array) {
        write(key, () -> valueWriter.writeIntegerArray(array));
    }

    @Override
    public void writeIntegerArray(String key, Collection<Integer> array) {
        write(key, () -> valueWriter.writeIntegerArray(array));
    }

    /* LONG */

    @Override
    public void writeValue(String key, long value, JsonLongSerializer serializer) {
        writeLong(key, value, serializer);
    }

    @Override
    public void writeLong(String key, long value, JsonLongSerializer serializer) {
        write(key, () -> valueWriter.writeLong(value, serializer));
    }

    @Override
    public void writeLong(String key, Long value, JsonSerializer<Long> serializer) {
        write(key, () -> valueWriter.writeLong(value, serializer));
    }

    @Override
    public void writeLongArray(String key, long[] array, JsonLongSerializer serializer) {
        write(key, () -> valueWriter.writeLongArray(array, serializer));
    }

    @Override
    public void writeLongArray(String key, Long[] array, JsonSerializer<Long> serializer) {
        write(key, () -> valueWriter.writeLongArray(array, serializer));
    }

    @Override
    public void writeLongArray(String key, Collection<Long> array, JsonSerializer<Long> serializer) {
        write(key, () -> valueWriter.writeLongArray(array, serializer));
    }

    @Override
    public void writeLong(String key, long value) {
        write(key, () -> valueWriter.writeLong(value));
    }

    @Override
    public void writeLong(String key, Long value) {
        write(key, () -> valueWriter.writeLong(value));
    }

    @Override
    public void writeLongArray(String key, long[] array) {
        write(key, () -> valueWriter.writeLongArray(array));
    }

    @Override
    public void writeLongArray(String key, Long[] array) {
        write(key, () -> valueWriter.writeLongArray(array));
    }

    @Override
    public void writeLongArray(String key, Collection<Long> array) {
        write(key, () -> valueWriter.writeLongArray(array));
    }

    /* FLOAT */

    @Override
    public void writeValue(String key, float value, JsonFloatSerializer serializer) {
        writeFloat(key, value, serializer);
    }

    @Override
    public void writeFloat(String key, float value, JsonFloatSerializer serializer) {
        write(key, () -> valueWriter.writeFloat(value, serializer));
    }

    @Override
    public void writeFloat(String key, Float value, JsonSerializer<Float> serializer) {
        write(key, () -> valueWriter.writeFloat(value, serializer));
    }

    @Override
    public void writeFloatArray(String key, float[] array, JsonFloatSerializer serializer) {
        write(key, () -> valueWriter.writeFloatArray(array, serializer));
    }

    @Override
    public void writeFloatArray(String key, Float[] array, JsonSerializer<Float> serializer) {
        write(key, () -> valueWriter.writeFloatArray(array, serializer));
    }

    @Override
    public void writeFloatArray(String key, Collection<Float> array, JsonSerializer<Float> serializer) {
        write(key, () -> valueWriter.writeFloatArray(array, serializer));
    }

    @Override
    public void writeFloat(String key, float value) {
        write(key, () -> valueWriter.writeFloat(value));
    }

    @Override
    public void writeFloat(String key, Float value) {
        write(key, () -> valueWriter.writeFloat(value));
    }

    @Override
    public void writeFloatArray(String key, float[] array) {
        write(key, () -> valueWriter.writeFloatArray(array));
    }

    @Override
    public void writeFloatArray(String key, Float[] array) {
        write(key, () -> valueWriter.writeFloatArray(array));
    }

    @Override
    public void writeFloatArray(String key, Collection<Float> array) {
        write(key, () -> valueWriter.writeFloatArray(array));
    }

    /* DOUBLE */

    @Override
    public void writeValue(String key, double value, JsonDoubleSerializer serializer) {
        writeDouble(key, value, serializer);
    }

    @Override
    public void writeDouble(String key, double value, JsonDoubleSerializer serializer) {
        write(key, () -> valueWriter.writeDouble(value, serializer));
    }

    @Override
    public void writeDouble(String key, Double value, JsonSerializer<Double> serializer) {
        write(key, () -> valueWriter.writeDouble(value, serializer));
    }

    @Override
    public void writeDoubleArray(String key, double[] array, JsonDoubleSerializer serializer) {
        write(key, () -> valueWriter.writeDoubleArray(array, serializer));
    }

    @Override
    public void writeDoubleArray(String key, Double[] array, JsonSerializer<Double> serializer) {
        write(key, () -> valueWriter.writeDoubleArray(array, serializer));
    }

    @Override
    public void writeDoubleArray(String key, Collection<Double> array, JsonSerializer<Double> serializer) {
        write(key, () -> valueWriter.writeDoubleArray(array, serializer));
    }

    @Override
    public void writeDouble(String key, double value) {
        write(key, () -> valueWriter.writeDouble(value));
    }

    @Override
    public void writeDouble(String key, Double value) {
        write(key, () -> valueWriter.writeDouble(value));
    }

    @Override
    public void writeDoubleArray(String key, double[] array) {
        write(key, () -> valueWriter.writeDoubleArray(array));
    }

    @Override
    public void writeDoubleArray(String key, Double[] array) {
        write(key, () -> valueWriter.writeDoubleArray(array));
    }

    @Override
    public void writeDoubleArray(String key, Collection<Double> array) {
        write(key, () -> valueWriter.writeDoubleArray(array));
    }

    /* BIG INTEGER */

    @Override
    public void writeBigInteger(String key, BigInteger value, JsonSerializer<BigInteger> serializer) {
        write(key, () -> valueWriter.writeBigInteger(value, serializer));
    }

    @Override
    public void writeBigIntegerArray(String key, BigInteger[] array, JsonSerializer<BigInteger> serializer) {
        write(key, () -> valueWriter.writeBigIntegerArray(array, serializer));
    }

    @Override
    public void writeBigIntegerArray(String key, Collection<BigInteger> array, JsonSerializer<BigInteger> serializer) {
        write(key, () -> valueWriter.writeBigIntegerArray(array, serializer));
    }

    @Override
    public void writeBigInteger(String key, BigInteger value) {
        write(key, () -> valueWriter.writeBigInteger(value));
    }

    @Override
    public void writeBigIntegerArray(String key, BigInteger[] array) {
        write(key, () -> valueWriter.writeBigIntegerArray(array));
    }

    @Override
    public void writeBigIntegerArray(String key, Collection<BigInteger> array) {
        write(key, () -> valueWriter.writeBigIntegerArray(array));
    }

    /* BIG DECIMAL */

    @Override
    public void writeBigDecimal(String key, BigDecimal value, JsonSerializer<BigDecimal> serializer) {
        write(key, () -> valueWriter.writeBigDecimal(value, serializer));
    }

    @Override
    public void writeBigDecimalArray(String key, BigDecimal[] array, JsonSerializer<BigDecimal> serializer) {
        write(key, () -> valueWriter.writeBigDecimalArray(array, serializer));
    }

    @Override
    public void writeBigDecimalArray(String key, Collection<BigDecimal> array, JsonSerializer<BigDecimal> serializer) {
        write(key, () -> valueWriter.writeBigDecimalArray(array, serializer));
    }

    @Override
    public void writeBigDecimal(String key, BigDecimal value) {
        write(key, () -> valueWriter.writeBigDecimal(value));
    }

    @Override
    public void writeBigDecimalArray(String key, BigDecimal[] array) {
        write(key, () -> valueWriter.writeBigDecimalArray(array));
    }

    @Override
    public void writeBigDecimalArray(String key, Collection<BigDecimal> array) {
        write(key, () -> valueWriter.writeBigDecimalArray(array));
    }

    /* STRING */

    @Override
    public void writeString(String key, String value, JsonSerializer<String> serializer) {
        write(key, () -> valueWriter.writeString(value, serializer));
    }

    @Override
    public void writeStringArray(String key, String[] array, JsonSerializer<String> serializer) {
        write(key, () -> valueWriter.writeStringArray(array, serializer));
    }

    @Override
    public void writeStringArray(String key, Collection<String> array, JsonSerializer<String> serializer) {
        write(key, () -> valueWriter.writeStringArray(array, serializer));
    }

    @Override
    public void writeString(String key, String value) {
        write(key, () -> valueWriter.writeString(value));
    }

    @Override
    public void writeStringArray(String key, String[] array) {
        write(key, () -> valueWriter.writeStringArray(array));
    }

    @Override
    public void writeStringArray(String key, Collection<String> array) {
        write(key, () -> valueWriter.writeStringArray(array));
    }

    /* CHARACTER */

    @Override
    public void writeCharacter(String key, char value, JsonCharacterSerializer serializer) {
        write(key, () -> valueWriter.writeCharacter(value, serializer));
    }

    @Override
    public void writeCharacter(String key, Character value, JsonSerializer<Character> serializer) {
        write(key, () -> valueWriter.writeCharacter(value, serializer));
    }

    @Override
    public void writeCharacterArray(String key, char[] array, JsonCharacterSerializer serializer) {
        write(key, () -> valueWriter.writeCharacterArray(array, serializer));
    }

    @Override
    public void writeCharacterArray(String key, Character[] array, JsonSerializer<Character> serializer) {
        write(key, () -> valueWriter.writeCharacterArray(array, serializer));
    }

    @Override
    public void writeCharacterArray(String key, Collection<Character> array, JsonSerializer<Character> serializer) {
        write(key, () -> valueWriter.writeCharacterArray(array, serializer));
    }

    @Override
    public void writeCharacter(String key, char value) {
        write(key, () -> valueWriter.writeCharacter(value));
    }

    @Override
    public void writeCharacter(String key, Character value) {
        write(key, () -> valueWriter.writeCharacter(value));
    }

    @Override
    public void writeCharacterArray(String key, char[] array) {
        write(key, () -> valueWriter.writeCharacterArray(array));
    }

    @Override
    public void writeCharacterArray(String key, Character[] array) {
        write(key, () -> valueWriter.writeCharacterArray(array));
    }

    @Override
    public void writeCharacterArray(String key, Collection<Character> array) {
        write(key, () -> valueWriter.writeCharacterArray(array));
    }

    /* CUSTOM */

    @Override
    public <T> void writeValue(String key, T value, JsonSerializer<T> serializer) {
        write(key, () -> valueWriter.writeValue(value, serializer));
    }

    @Override
    public <T> void writeValueArray(String key, T[] array, JsonSerializer<T> serializer) {
        write(key, () -> valueWriter.writeValueArray(array, serializer));
    }

    @Override
    public <T> void writeValueArray(String key, Collection<T> array, JsonSerializer<T> serializer) {
        write(key, () -> valueWriter.writeValueArray(array, serializer));
    }

    /* OBJECT */

    @Override
    public <T> void writeObject(String key, T value, JsonObjectSerializer<T> serializer) {
        write(key, () -> valueWriter.writeObject(value, serializer));
    }

    @Override
    public <T> void writeObjectArray(String key, T[] array, JsonObjectSerializer<T> serializer) {
        write(key, () -> valueWriter.writeObjectArray(array, serializer));
    }

    @Override
    public <T> void writeObjectArray(String key, Collection<T> array, JsonObjectSerializer<T> serializer) {
        write(key, () -> valueWriter.writeObjectArray(array, serializer));
    }

    /* NULL */

    @Override
    public void writeNull(String key) {
        write(key, valueWriter::writeNull);
    }
}
