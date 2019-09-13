package guru.bug.austras.convert.converters.json;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

import java.math.BigInteger;

@Component
public class BigIntegerToJsonConverter implements JsonConverter<BigInteger> {
    @Override
    public void toJson(BigInteger value, JsonValueWriter writer) {
        writer.write(value);
    }

    @Override
    public BigInteger fromJson(JsonValueReader reader) {
        return reader.readNullableBigInteger();
    }

}
