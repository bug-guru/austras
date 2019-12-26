package guru.bug.austras.convert.converters.json;


import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

import java.math.BigDecimal;

@Default
public class BigDecimalToJsonConverter implements JsonConverter<BigDecimal> {
    @Override
    public void toJson(BigDecimal value, JsonValueWriter writer) {
        writer.writeBigDecimal(value);
    }

    @Override
    public BigDecimal fromJson(JsonValueReader reader) {
        return reader.readNullableBigDecimal();
    }

}
