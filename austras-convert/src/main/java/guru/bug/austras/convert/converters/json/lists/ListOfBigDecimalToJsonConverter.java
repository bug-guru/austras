package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.math.BigDecimal;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfBigDecimalToJsonConverter extends ListToJsonConverter<BigDecimal> {

    public ListOfBigDecimalToJsonConverter(JsonConverter<BigDecimal> elementConverter) {
        super(elementConverter);
    }

}
