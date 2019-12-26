package guru.bug.austras.convert.engine.json.writer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

@SuppressWarnings("unused")
public interface JsonObjectWriter {

    /* BOOLEAN */

    void writeBoolean(String key, boolean value, JsonBooleanSerializer serializer);

    void writeBoolean(String key, Boolean value, JsonSerializer<Boolean> serializer);

    void writeBooleanArray(String key, boolean[] array, JsonBooleanSerializer serializer);

    void writeBooleanArray(String key, Boolean[] array, JsonSerializer<Boolean> serializer);

    void writeBooleanArray(String key, Collection<Boolean> array, JsonSerializer<Boolean> serializer);

    void writeBoolean(String key, boolean value);

    void writeBoolean(String key, Boolean value);

    void writeBooleanArray(String key, boolean[] array);

    void writeBooleanArray(String key, Boolean[] array);

    void writeBooleanArray(String key, Collection<Boolean> array);

    /* BYTE */

    void writeByte(String key, byte value, JsonByteSerializer serializer);

    void writeByte(String key, Byte value, JsonSerializer<Byte> serializer);

    void writeByteArray(String key, byte[] array, JsonByteSerializer serializer);

    void writeByteArray(String key, Byte[] array, JsonSerializer<Byte> serializer);

    void writeByteArray(String key, Collection<Byte> array, JsonSerializer<Byte> serializer);

    void writeByte(String key, byte value);

    void writeByte(String key, Byte value);

    void writeByteArray(String key, byte[] array);

    void writeByteArray(String key, Byte[] array);

    void writeByteArray(String key, Collection<Byte> array);

    /* SHORT */

    void writeShort(String key, short value, JsonShortSerializer serializer);

    void writeShort(String key, Short value, JsonSerializer<Short> serializer);

    void writeShortArray(String key, short[] array, JsonShortSerializer serializer);

    void writeShortArray(String key, Short[] array, JsonSerializer<Short> serializer);

    void writeShortArray(String key, Collection<Short> array, JsonSerializer<Short> serializer);

    void writeShort(String key, short value);

    void writeShort(String key, Short value);

    void writeShortArray(String key, short[] array);

    void writeShortArray(String key, Short[] array);

    void writeShortArray(String key, Collection<Short> array);

    /* INTEGER */

    void writeInteger(String key, int value, JsonIntegerSerializer serializer);

    void writeInteger(String key, Integer value, JsonSerializer<Integer> serializer);

    void writeIntegerArray(String key, int[] array, JsonIntegerSerializer serializer);

    void writeIntegerArray(String key, Integer[] array, JsonSerializer<Integer> serializer);

    void writeIntegerArray(String key, Collection<Integer> array, JsonSerializer<Integer> serializer);

    void writeInteger(String key, int value);

    void writeInteger(String key, Integer value);

    void writeIntegerArray(String key, int[] array);

    void writeIntegerArray(String key, Integer[] array);

    void writeIntegerArray(String key, Collection<Integer> array);

    /* LONG */

    void writeLong(String key, long value, JsonLongSerializer serializer);

    void writeLong(String key, Long value, JsonSerializer<Long> serializer);

    void writeLongArray(String key, long[] array, JsonLongSerializer serializer);

    void writeLongArray(String key, Long[] array, JsonSerializer<Long> serializer);

    void writeLongArray(String key, Collection<Long> array, JsonSerializer<Long> serializer);

    void writeLong(String key, long value);

    void writeLong(String key, Long value);

    void writeLongArray(String key, long[] array);

    void writeLongArray(String key, Long[] array);

    void writeLongArray(String key, Collection<Long> array);

    /* FLOAT */

    void writeFloat(String key, float value, JsonFloatSerializer serializer);

    void writeFloat(String key, Float value, JsonSerializer<Float> serializer);

    void writeFloatArray(String key, float[] array, JsonFloatSerializer serializer);

    void writeFloatArray(String key, Float[] array, JsonSerializer<Float> serializer);

    void writeFloatArray(String key, Collection<Float> array, JsonSerializer<Float> serializer);

    void writeFloat(String key, float value);

    void writeFloat(String key, Float value);

    void writeFloatArray(String key, float[] array);

    void writeFloatArray(String key, Float[] array);

    void writeFloatArray(String key, Collection<Float> array);

    /* DOUBLE */

    void writeDouble(String key, double value, JsonDoubleSerializer serializer);

    void writeDouble(String key, Double value, JsonSerializer<Double> serializer);

    void writeDoubleArray(String key, double[] array, JsonDoubleSerializer serializer);

    void writeDoubleArray(String key, Double[] array, JsonSerializer<Double> serializer);

    void writeDoubleArray(String key, Collection<Double> array, JsonSerializer<Double> serializer);

    void writeDouble(String key, double value);

    void writeDouble(String key, Double value);

    void writeDoubleArray(String key, double[] array);

    void writeDoubleArray(String key, Double[] array);

    void writeDoubleArray(String key, Collection<Double> array);

    /* BIG INTEGER */

    void writeBigInteger(String key, BigInteger value, JsonSerializer<BigInteger> serializer);

    void writeBigIntegerArray(String key, BigInteger[] array, JsonSerializer<BigInteger> serializer);

    void writeBigIntegerArray(String key, Collection<BigInteger> array, JsonSerializer<BigInteger> serializer);

    void writeBigInteger(String key, BigInteger value);

    void writeBigIntegerArray(String key, BigInteger[] array);

    void writeBigIntegerArray(String key, Collection<BigInteger> array);

    /* BIG DECIMAL */

    void writeBigDecimal(String key, BigDecimal value, JsonSerializer<BigDecimal> serializer);

    void writeBigDecimalArray(String key, BigDecimal[] array, JsonSerializer<BigDecimal> serializer);

    void writeBigDecimalArray(String key, Collection<BigDecimal> array, JsonSerializer<BigDecimal> serializer);

    void writeBigDecimal(String key, BigDecimal value);

    void writeBigDecimalArray(String key, BigDecimal[] array);

    void writeBigDecimalArray(String key, Collection<BigDecimal> array);

    /* STRING */

    void writeString(String key, String value, JsonSerializer<String> serializer);

    void writeStringArray(String key, String[] array, JsonSerializer<String> serializer);

    void writeStringArray(String key, Collection<String> array, JsonSerializer<String> serializer);

    void writeString(String key, String value);

    void writeStringArray(String key, String[] array);

    void writeStringArray(String key, Collection<String> array);

    /* CHARACTER */

    void writeCharacter(String key, char value, JsonCharacterSerializer serializer);

    void writeCharacter(String key, Character value, JsonSerializer<Character> serializer);

    void writeCharacterArray(String key, char[] array, JsonCharacterSerializer serializer);

    void writeCharacterArray(String key, Character[] array, JsonSerializer<Character> serializer);

    void writeCharacterArray(String key, Collection<Character> array, JsonSerializer<Character> serializer);

    void writeCharacter(String key, char value);

    void writeCharacter(String key, Character value);

    void writeCharacterArray(String key, char[] array);

    void writeCharacterArray(String key, Character[] array);

    void writeCharacterArray(String key, Collection<Character> array);

    /* CUSTOM */

    <T> void writeValue(String key, T value, JsonSerializer<T> serializer);

    <T> void writeValueArray(String key, T[] array, JsonSerializer<T> serializer);

    <T> void writeValueArray(String key, Collection<T> array, JsonSerializer<T> serializer);

    /* OBJECT */

    <T> void writeObject(String key, T value, JsonObjectSerializer<T> serializer);

    <T> void writeObjectArray(String key, T[] array, JsonObjectSerializer<T> serializer);

    <T> void writeObjectArray(String key, Collection<T> array, JsonObjectSerializer<T> serializer);

    /* NULL */

    void writeNull(String key);
}
