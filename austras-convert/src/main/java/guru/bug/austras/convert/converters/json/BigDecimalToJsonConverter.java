package guru.bug.austras.convert.converters.json;


import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

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
