package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.math.BigDecimal;

@Component
public class SetOfBigDecimalToJsonConverter extends SetToJsonConverter<BigDecimal> {

    public SetOfBigDecimalToJsonConverter(JsonConverter<BigDecimal> elementConverter) {
        super(elementConverter);
    }

}
