package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.math.BigDecimal;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfBigDecimalJsonConverter extends SetJsonConverter<BigDecimal> {

    public SetOfBigDecimalJsonConverter(JsonConverter<BigDecimal> elementConverter) {
        super(elementConverter);
    }

}
