package guru.bug.austras.convert.converters.json;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

import java.math.BigDecimal;

@Component
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
