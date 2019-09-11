package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;

import java.math.BigDecimal;

public class ListOfBigDecimalToJsonConverter extends ListToJsonConverter<BigDecimal> {

    public ListOfBigDecimalToJsonConverter(JsonConverter<BigDecimal> elementConverter) {
        super(elementConverter);
    }

}
