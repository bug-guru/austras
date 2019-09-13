package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.math.BigDecimal;

@Component
public class ListOfBigDecimalToJsonConverter extends ListToJsonConverter<BigDecimal> {

    public ListOfBigDecimalToJsonConverter(JsonConverter<BigDecimal> elementConverter) {
        super(elementConverter);
    }

}
