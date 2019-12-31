package guru.bug.austras.convert.content.json;


import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.math.BigDecimal;

@ApplicationJson
public class BigDecimalJsonConverter extends AbstractJsonConverter<BigDecimal> {
    @Override
    public void toJson(BigDecimal value, JsonValueWriter writer) {
        writer.writeBigDecimal(value);
    }

    @Override
    public BigDecimal fromJson(JsonValueReader reader) {
        return reader.readNullableBigDecimal();
    }

}
