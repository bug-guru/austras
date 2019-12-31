package guru.bug.austras.convert.content.json;


import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.math.BigInteger;

@ApplicationJson
public class BigIntegerJsonConverter extends AbstractJsonConverter<BigInteger> {
    @Override
    public void toJson(BigInteger value, JsonValueWriter writer) {
        writer.writeBigInteger(value);
    }

    @Override
    public BigInteger fromJson(JsonValueReader reader) {
        return reader.readNullableBigInteger();
    }

}
