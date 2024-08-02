/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.json.reader;

import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface JsonValueReader {

    static JsonValueReader newInstance(Reader reader) {
        var tokenReader = new JsonTokenReader(reader);
        return new JsonValueReaderImpl(tokenReader);
    }

    /* BOOLEAN */

    boolean readBoolean();

    Boolean readNullableBoolean();

    Optional<Boolean> readOptionalBoolean();

    Optional<Stream<Boolean>> readBooleanArray();

    /* BYTE */

    byte readByte();

    Byte readNullableByte();

    Optional<Byte> readOptionalByte();

    Optional<Stream<Byte>> readByteArray();

    /* SHORT */

    short readShort();

    Short readNullableShort();

    Optional<Short> readOptionalShort();

    Optional<Stream<Short>> readShortArray();

    /* INTEGER */

    int readInt();

    Integer readNullableInt();

    Optional<Integer> readOptionalInteger();

    Optional<Stream<Integer>> readIntegerArray();

    /* LONG */

    long readLong();

    Long readNullableLong();

    Optional<Long> readOptionalLong();

    Optional<Stream<Long>> readLongArray();

    /* FLOAT */

    float readFloat();

    Float readNullableFloat();

    Optional<Float> readOptionalFloat();

    Optional<Stream<Float>> readFloatArray();

    /* DOUBLE */

    double readDouble();

    Double readNullableDouble();

    Optional<Double> readOptionalDouble();

    Optional<Stream<Double>> readDoubleArray();

    /* BIG INTEGER */

    BigInteger readBigInteger();

    BigInteger readNullableBigInteger();

    Optional<BigInteger> readOptionalBigInteger();

    Optional<Stream<BigInteger>> readBigIntegerArray();

    /* BIG DECIMAL */

    BigDecimal readBigDecimal();

    BigDecimal readNullableBigDecimal();

    Optional<BigDecimal> readOptionalBigDecimal();

    Optional<Stream<BigDecimal>> readBigDecimalArray();

    /* STRING */

    String readString();

    String readNullableString();

    Optional<String> readOptionalString();

    Optional<Stream<String>> readStringArray();

    /* CHARACTER */

    char readChar();

    Character readNullableChar();

    Optional<Character> readOptionalChar();

    Optional<Stream<Character>> readCharArray();

    /* CUSTOM */

    boolean read(JsonBooleanDeserializer converter);

    byte read(JsonByteDeserializer converter);

    char read(JsonCharacterDeserializer converter);

    double read(JsonDoubleDeserializer converter);

    float read(JsonFloatDeserializer converter);

    int read(JsonIntegerDeserializer converter);

    long read(JsonLongDeserializer converter);

    short read(JsonShortDeserializer converter);

    <T> Optional<T> read(JsonDeserializer<T> converter);

    <T> Optional<Stream<T>> readOptionalArray(JsonDeserializer<T> converter);

    /* OBJECT */

    <T> T readNullableObject(Supplier<T> resultSupplier, JsonObjectMemberVisitor<T> memberVisitor);

    <T> Optional<T> readOptionalObject(Supplier<T> resultSupplier, JsonObjectMemberVisitor<T> memberVisitor);

    <T> Optional<Stream<T>> readObjectArray(Supplier<T> resultSupplier, JsonObjectMemberVisitor<T> memberVisitor);

}
