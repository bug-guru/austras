package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.math.BigDecimal;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfBigDecimalToJsonConverter extends SetToJsonConverter<BigDecimal> {

    public SetOfBigDecimalToJsonConverter(JsonConverter<BigDecimal> elementConverter) {
        super(elementConverter);
    }

}
