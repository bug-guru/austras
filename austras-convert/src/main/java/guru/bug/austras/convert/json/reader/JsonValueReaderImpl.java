package guru.bug.austras.convert.json.reader;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static guru.bug.austras.convert.json.reader.TokenType.*;

class JsonValueReaderImpl implements JsonValueReader {
    private static final String CHAR_WAS_EXPECTING = "Char was expecting";
    private final JsonTokenReader tokenReader;

    JsonValueReaderImpl(JsonTokenReader tokenReader) {
        this.tokenReader = tokenReader;
    }

    @Override
    public boolean readBoolean() {
        var tt = tokenReader.next(TRUE, FALSE);
        return tt == TRUE;
    }

    @Override
    public Boolean readNullableBoolean() {
        var tt = tokenReader.nextNullableBoolean();
        if (tt == NULL) {
            return null; //NOSONAR reading null from json is OK
        }
        return tt == TRUE;
    }

    @Override
    public Optional<Boolean> readOptionalBoolean() {
        var tt = tokenReader.nextNullableBoolean();
        if (tt == NULL) {
            return Optional.empty();
        }
        return Optional.of(tt == TRUE);
    }

    @Override
    public Optional<Stream<Boolean>> readBooleanArray() {
        return readOptionalArray(JsonValueReader::readNullableBoolean);
    }

    @Override
    public byte readByte() {
        tokenReader.next(NUMBER);
        return Byte.parseByte(tokenReader.getValue());
    }

    @Override
    public Byte readNullableByte() {
        if (tokenReader.next(NUMBER, NULL) == NULL) {
            return null;
        } else {
            return Byte.parseByte(tokenReader.getValue());
        }
    }

    @Override
    public Optional<Byte> readOptionalByte() {
        if (tokenReader.next(NUMBER, NULL) == NULL) {
            return Optional.empty();
        } else {
            String value = tokenReader.getValue();
            return Optional.of(Byte.parseByte(value));
        }
    }

    @Override
    public Optional<Stream<Byte>> readByteArray() {
        return readOptionalArray(JsonValueReader::readNullableByte);
    }

    @Override
    public short readShort() {
        tokenReader.next(NUMBER);
        return Short.parseShort(tokenReader.getValue());
    }

    @Override
    public Short readNullableShort() {
        if (tokenReader.next(NUMBER, NULL) == NULL) {
            return null;
        } else {
            return Short.parseShort(tokenReader.getValue());
        }
    }

    @Override
    public Optional<Short> readOptionalShort() {
        if (tokenReader.next(NUMBER, NULL) == NULL) {
            return Optional.empty();
        } else {
            return Optional.of(Short.parseShort(tokenReader.getValue()));
        }
    }

    @Override
    public Optional<Stream<Short>> readShortArray() {
        return readOptionalArray(JsonValueReader::readNullableShort);
    }

    @Override
    public int readInt() {
        tokenReader.next(NUMBER);
        return Integer.parseInt(tokenReader.getValue());
    }

    @Override
    public Integer readNullableInt() {
        var tt = tokenReader.next(NULL, NUMBER);
        if (tt == NULL) {
            return null;
        } else {
            return Integer.parseInt(tokenReader.getValue());
        }
    }

    @Override
    public Optional<Integer> readOptionalInteger() {
        var tt = tokenReader.next(NULL, NUMBER);
        if (tt == NULL) {
            return Optional.empty();
        } else {
            return Optional.of(Integer.parseInt(tokenReader.getValue()));
        }
    }

    @Override
    public Optional<Stream<Integer>> readIntegerArray() {
        return readOptionalArray(JsonValueReader::readNullableInt);
    }

    @Override
    public long readLong() {
        tokenReader.next(NUMBER);
        return Long.parseLong(tokenReader.getValue());
    }

    @Override
    public Long readNullableLong() {
        var tt = tokenReader.next(NULL, NUMBER);
        if (tt == NULL) {
            return null;
        } else {
            return Long.parseLong(tokenReader.getValue());
        }
    }

    @Override
    public Optional<Long> readOptionalLong() {
        var tt = tokenReader.next(NULL, NUMBER);
        if (tt == NULL) {
            return Optional.empty();
        } else {
            return Optional.of(Long.parseLong(tokenReader.getValue()));
        }
    }

    @Override
    public Optional<Stream<Long>> readLongArray() {
        return readOptionalArray(JsonValueReader::readNullableLong);
    }

    @Override
    public float readFloat() {
        tokenReader.next(NUMBER);
        return Float.parseFloat(tokenReader.getValue());
    }

    @Override
    public Float readNullableFloat() {
        var tt = tokenReader.next(NULL, NUMBER);
        if (tt == NULL) {
            return null;
        } else {
            return Float.parseFloat(tokenReader.getValue());
        }
    }

    @Override
    public Optional<Float> readOptionalFloat() {
        var tt = tokenReader.next(NULL, NUMBER);
        if (tt == NULL) {
            return Optional.empty();
        } else {
            return Optional.of(Float.parseFloat(tokenReader.getValue()));
        }
    }

    @Override
    public Optional<Stream<Float>> readFloatArray() {
        return readOptionalArray(JsonValueReader::readNullableFloat);
    }

    @Override
    public double readDouble() {
        tokenReader.next(NUMBER);
        return Double.parseDouble(tokenReader.getValue());
    }

    @Override
    public Double readNullableDouble() {
        var tt = tokenReader.next(NULL, NUMBER);
        if (tt == NULL) {
            return null;
        } else {
            return Double.parseDouble(tokenReader.getValue());
        }
    }

    @Override
    public Optional<Double> readOptionalDouble() {
        var tt = tokenReader.next(NULL, NUMBER);
        if (tt == NULL) {
            return Optional.empty();
        } else {
            return Optional.of(Double.parseDouble(tokenReader.getValue()));
        }
    }

    @Override
    public Optional<Stream<Double>> readDoubleArray() {
        return readOptionalArray(JsonValueReader::readNullableDouble);
    }

    @Override
    public BigInteger readBigInteger() {
        tokenReader.next(NUMBER);
        return new BigInteger(tokenReader.getValue());
    }

    @Override
    public BigInteger readNullableBigInteger() {
        var tt = tokenReader.next(NULL, NUMBER);
        if (tt == NULL) {
            return null;
        } else {
            return new BigInteger(tokenReader.getValue());
        }
    }

    @Override
    public Optional<BigInteger> readOptionalBigInteger() {
        var tt = tokenReader.next(NULL, NUMBER);
        if (tt == NULL) {
            return Optional.empty();
        } else {
            return Optional.of(new BigInteger(tokenReader.getValue()));
        }
    }

    @Override
    public Optional<Stream<BigInteger>> readBigIntegerArray() {
        return readOptionalArray(JsonValueReader::readNullableBigInteger);
    }

    @Override
    public BigDecimal readBigDecimal() {
        tokenReader.next(NUMBER);
        return new BigDecimal(tokenReader.getValue());
    }

    @Override
    public BigDecimal readNullableBigDecimal() {
        var tt = tokenReader.next(NULL, NUMBER);
        if (tt == NULL) {
            return null;
        } else {
            return new BigDecimal(tokenReader.getValue());
        }
    }

    @Override
    public Optional<BigDecimal> readOptionalBigDecimal() {
        var tt = tokenReader.next(NULL, NUMBER);
        if (tt == NULL) {
            return Optional.empty();
        } else {
            return Optional.of(new BigDecimal(tokenReader.getValue()));
        }
    }

    @Override
    public Optional<Stream<BigDecimal>> readBigDecimalArray() {
        return readOptionalArray(JsonValueReader::readNullableBigDecimal);
    }

    @Override
    public String readString() {
        tokenReader.next(STRING);
        return tokenReader.getValue();
    }

    @Override
    public String readNullableString() {
        var tt = tokenReader.next(NULL, STRING);
        if (tt == NULL) {
            return null;
        } else {
            return tokenReader.getValue();
        }
    }

    @Override
    public Optional<String> readOptionalString() {
        var tt = tokenReader.next(NULL, STRING);
        if (tt == NULL) {
            return Optional.empty();
        } else {
            return Optional.of(tokenReader.getValue());
        }
    }

    @Override
    public Optional<Stream<String>> readStringArray() {
        return readOptionalArray(JsonValueReader::readNullableString);
    }

    @Override
    public char readChar() {
        tokenReader.next(STRING);
        var str = tokenReader.getValue();
        if (str.length() > 1) {
            throw tokenReader.createParsingException(CHAR_WAS_EXPECTING);
        }
        return str.charAt(0);
    }

    @Override
    public Character readNullableChar() {
        if (tokenReader.next(STRING, NULL) == NULL) {
            return null;
        }
        var str = tokenReader.getValue();
        if (str.length() > 1) {
            throw tokenReader.createParsingException(CHAR_WAS_EXPECTING);
        }
        return str.charAt(0);
    }

    @Override
    public Optional<Character> readOptionalChar() {
        if (tokenReader.next(STRING, NULL) == NULL) {
            return Optional.empty();
        }
        var str = tokenReader.getValue();
        if (str.length() > 1) {
            throw tokenReader.createParsingException(CHAR_WAS_EXPECTING);
        }
        return Optional.of(str.charAt(0));
    }

    @Override
    public Optional<Stream<Character>> readCharArray() {
        return readOptionalArray(JsonValueReader::readNullableChar);
    }

    @Override
    public boolean read(JsonBooleanDeserializer converter) {
        return converter.fromJson(this);
    }

    @Override
    public byte read(JsonByteDeserializer converter) {
        return converter.fromJson(this);
    }

    @Override
    public char read(JsonCharDeserializer converter) {
        return converter.fromJson(this);
    }

    @Override
    public double read(JsonDoubleDeserializer converter) {
        return converter.fromJson(this);
    }

    @Override
    public float read(JsonFloatDeserializer converter) {
        return converter.fromJson(this);
    }

    @Override
    public int read(JsonIntegerDeserializer converter) {
        return converter.fromJson(this);
    }

    @Override
    public long read(JsonLongDeserializer converter) {
        return converter.fromJson(this);
    }

    @Override
    public short read(JsonShortDeserializer converter) {
        return converter.fromJson(this);
    }

    @Override
    public <T> Optional<T> read(JsonDeserializer<T> converter) {
        if (tokenReader.next() == NULL) {
            return Optional.empty();
        }
        tokenReader.rewind();
        var value = converter.fromJson(this);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(value);
    }

    @Override
    public <T> Optional<Stream<T>> readOptionalArray(JsonDeserializer<T> converter) {
        if (tokenReader.next(BEGIN_ARRAY, NULL) == NULL) {
            return Optional.empty();
        }
        var tokenType = tokenReader.next(EnumSet.of(END_ARRAY, BEGIN_OBJECT, NULL, NUMBER, STRING, TRUE, FALSE, BEGIN_ARRAY));
        if (tokenType == END_ARRAY) {
            return Optional.of(Stream.empty());
        }
        tokenReader.rewind();
        Stream<T> stream = StreamSupport.stream(new JsonArraySpliterator<>(converter), false);
        return Optional.of(stream);
    }

    @Override
    public <T> T readNullableObject(Supplier<T> resultSupplier, JsonObjectMemberVisitor<T> memberVisitor) {
        if (tokenReader.next(BEGIN_OBJECT, NULL) == NULL) {
            return null;
        }
        var result = resultSupplier.get();
        if (tokenReader.next(STRING, END_OBJECT) == END_OBJECT) {
            return result;
        }
        for (; ; ) {
            var key = tokenReader.getValue();
            tokenReader.next(COLON);
            var idx = tokenReader.getIndex();
            memberVisitor.accept(result, key, this);
            if (idx == tokenReader.getIndex()) {
                throw tokenReader.createParsingException("Unprocessed object member value: " + key);
            }
            if (tokenReader.next(COMMA, END_OBJECT) == END_OBJECT) {
                return result;
            }
            tokenReader.next(STRING);
        }
    }

    @Override
    public <T> Optional<T> readOptionalObject(Supplier<T> resultSupplier, JsonObjectMemberVisitor<T> memberVisitor) {
        return Optional.ofNullable(readNullableObject(resultSupplier, memberVisitor));
    }

    @Override
    public <T> Optional<Stream<T>> readObjectArray(Supplier<T> resultSupplier, JsonObjectMemberVisitor<T> memberVisitor) {
        return readOptionalArray(reader -> readNullableObject(resultSupplier, memberVisitor));
    }

    private class JsonArraySpliterator<T> implements Spliterator<T> {
        private final JsonDeserializer<T> converter;
        private boolean firstElement = true;

        private JsonArraySpliterator(JsonDeserializer<T> converter) {
            this.converter = converter;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (!firstElement) {
                var tokenType = tokenReader.next(END_ARRAY, COMMA);
                if (tokenType == END_ARRAY) {
                    return false;
                }
            }
            firstElement = false;
            var idx = tokenReader.getIndex();
            T value = converter.fromJson(JsonValueReaderImpl.this);
            if (idx == tokenReader.getIndex()) {
                throw tokenReader.createParsingException("Unprocessed array element");
            }
            action.accept(value);
            return true;
        }

        @Override
        public Spliterator<T> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return Long.MAX_VALUE;
        }

        @Override
        public int characteristics() {
            return IMMUTABLE;
        }
    }


}
