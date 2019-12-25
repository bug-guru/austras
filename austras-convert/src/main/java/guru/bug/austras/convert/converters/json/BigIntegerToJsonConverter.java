package guru.bug.austras.convert.converters.json;


import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.math.BigInteger;

@Default
public class BigIntegerToJsonConverter implements JsonConverter<BigInteger> {
    @Override
    public void toJson(BigInteger value, JsonValueWriter writer) {
        writer.writeBigInteger(value);
    }

    @Override
    public BigInteger fromJson(JsonValueReader reader) {
        return reader.readNullableBigInteger();
    }

}
