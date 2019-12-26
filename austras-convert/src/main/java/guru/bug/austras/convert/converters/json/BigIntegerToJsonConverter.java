package guru.bug.austras.convert.converters.json;


import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

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
