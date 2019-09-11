package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;

import java.math.BigDecimal;

public class SetOfBigDecimalToJsonConverter extends SetToJsonConverter<BigDecimal> {

    public SetOfBigDecimalToJsonConverter(JsonConverter<BigDecimal> elementConverter) {
        super(elementConverter);
    }

}
