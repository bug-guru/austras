/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.json.writer;

import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

public interface JsonValueWriter {

    static JsonValueWriter newInstance(Writer out) {
        var tokenWriter = new JsonTokenWriter(out);
        return new JsonValueWriterImpl(tokenWriter);
    }

    /* BOOLEAN */

    void writeBoolean(boolean value, JsonBooleanSerializer serializer);

    void writeBoolean(Boolean value, JsonSerializer<Boolean> serializer);

    void writeBooleanArray(boolean[] array, JsonBooleanSerializer serializer);

    void writeBooleanArray(Boolean[] array, JsonSerializer<Boolean> serializer);

    void writeBooleanArray(Collection<Boolean> array, JsonSerializer<Boolean> serializer);

    void writeBoolean(boolean value);

    void writeBoolean(Boolean value);

    void writeBooleanArray(boolean[] array);

    void writeBooleanArray(Boolean[] array);

    void writeBooleanArray(Collection<Boolean> array);

    /* BYTE */

    void writeByte(byte value, JsonByteSerializer serializer);

    void writeByte(Byte value, JsonSerializer<Byte> serializer);

    void writeByteArray(byte[] array, JsonByteSerializer serializer);

    void writeByteArray(Byte[] array, JsonSerializer<Byte> serializer);

    void writeByteArray(Collection<Byte> array, JsonSerializer<Byte> serializer);

    void writeByte(byte value);

    void writeByte(Byte value);

    void writeByteArray(byte[] array);

    void writeByteArray(Byte[] array);

    void writeByteArray(Collection<Byte> array);

    /* SHORT */

    void writeShort(short value, JsonShortSerializer serializer);

    void writeShort(Short value, JsonSerializer<Short> serializer);

    void writeShortArray(short[] array, JsonShortSerializer serializer);

    void writeShortArray(Short[] array, JsonSerializer<Short> serializer);

    void writeShortArray(Collection<Short> array, JsonSerializer<Short> serializer);

    void writeShort(short value);

    void writeShort(Short value);

    void writeShortArray(short[] array);

    void writeShortArray(Short[] array);

    void writeShortArray(Collection<Short> array);

    /* INTEGER */

    void writeInteger(int value, JsonIntegerSerializer serializer);

    void writeInteger(Integer value, JsonSerializer<Integer> serializer);

    void writeIntegerArray(int[] array, JsonIntegerSerializer serializer);

    void writeIntegerArray(Integer[] array, JsonSerializer<Integer> serializer);

    void writeIntegerArray(Collection<Integer> array, JsonSerializer<Integer> serializer);

    void writeInteger(int value);

    void writeInteger(Integer value);

    void writeIntegerArray(int[] array);

    void writeIntegerArray(Integer[] array);

    void writeIntegerArray(Collection<Integer> array);

    /* LONG */

    void writeLong(long value, JsonLongSerializer serializer);

    void writeLong(Long value, JsonSerializer<Long> serializer);

    void writeLongArray(long[] array, JsonLongSerializer serializer);

    void writeLongArray(Long[] array, JsonSerializer<Long> serializer);

    void writeLongArray(Collection<Long> array, JsonSerializer<Long> serializer);

    void writeLong(long value);

    void writeLong(Long value);

    void writeLongArray(long[] array);

    void writeLongArray(Long[] array);

    void writeLongArray(Collection<Long> array);

    /* FLOAT */

    void writeFloat(float value, JsonFloatSerializer serializer);

    void writeFloat(Float value, JsonSerializer<Float> serializer);

    void writeFloatArray(float[] array, JsonFloatSerializer serializer);

    void writeFloatArray(Float[] array, JsonSerializer<Float> serializer);

    void writeFloatArray(Collection<Float> array, JsonSerializer<Float> serializer);

    void writeFloat(float value);

    void writeFloat(Float value);

    void writeFloatArray(float[] array);

    void writeFloatArray(Float[] array);

    void writeFloatArray(Collection<Float> array);

    /* DOUBLE */

    void writeDouble(double value, JsonDoubleSerializer serializer);

    void writeDouble(Double value, JsonSerializer<Double> serializer);

    void writeDoubleArray(double[] array, JsonDoubleSerializer serializer);

    void writeDoubleArray(Double[] array, JsonSerializer<Double> serializer);

    void writeDoubleArray(Collection<Double> array, JsonSerializer<Double> serializer);

    void writeDouble(double value);

    void writeDouble(Double value);

    void writeDoubleArray(double[] array);

    void writeDoubleArray(Double[] array);

    void writeDoubleArray(Collection<Double> array);

    /* BIG INTEGER */

    void writeBigInteger(BigInteger value, JsonSerializer<BigInteger> serializer);

    void writeBigIntegerArray(BigInteger[] array, JsonSerializer<BigInteger> serializer);

    void writeBigIntegerArray(Collection<BigInteger> array, JsonSerializer<BigInteger> serializer);

    void writeBigInteger(BigInteger value);

    void writeBigIntegerArray(BigInteger[] array);

    void writeBigIntegerArray(Collection<BigInteger> array);

    /* BIG DECIMAL */

    void writeBigDecimal(BigDecimal value, JsonSerializer<BigDecimal> serializer);

    void writeBigDecimalArray(BigDecimal[] array, JsonSerializer<BigDecimal> serializer);

    void writeBigDecimalArray(Collection<BigDecimal> array, JsonSerializer<BigDecimal> serializer);

    void writeBigDecimal(BigDecimal value);

    void writeBigDecimalArray(BigDecimal[] array);

    void writeBigDecimalArray(Collection<BigDecimal> array);

    /* STRING */

    void writeString(String value, JsonSerializer<String> serializer);

    void writeStringArray(String[] array, JsonSerializer<String> serializer);

    void writeStringArray(Collection<String> array, JsonSerializer<String> serializer);

    void writeString(String value);

    void writeStringArray(String[] array);

    void writeStringArray(Collection<String> array);

    /* CHARACTER */

    void writeCharacter(char value, JsonCharacterSerializer serializer);

    void writeCharacter(Character value, JsonSerializer<Character> serializer);

    void writeCharacterArray(char[] array, JsonCharacterSerializer serializer);

    void writeCharacterArray(Character[] array, JsonSerializer<Character> serializer);

    void writeCharacterArray(Collection<Character> array, JsonSerializer<Character> serializer);

    void writeCharacter(char value);

    void writeCharacter(Character value);

    void writeCharacterArray(char[] array);

    void writeCharacterArray(Character[] array);

    void writeCharacterArray(Collection<Character> array);

    /* CUSTOM */

    <T> void writeValue(T value, JsonSerializer<T> serializer);

    <T> void writeValueArray(T[] array, JsonSerializer<T> serializer);

    <T> void writeValueArray(Collection<T> array, JsonSerializer<T> serializer);

    /* OBJECT */

    <T> void writeObject(T value, JsonObjectSerializer<T> serializer);

    <T> void writeObjectArray(T[] array, JsonObjectSerializer<T> serializer);

    <T> void writeObjectArray(Collection<T> array, JsonObjectSerializer<T> serializer);

    /* NULL */

    void writeNull();

}
