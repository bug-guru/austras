package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.math.BigDecimal;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfBigDecimalJsonConverter extends AbstractSetJsonConverter<BigDecimal> {

    public SetOfBigDecimalJsonConverter(@ApplicationJson JsonConverter<BigDecimal> elementConverter) {
        super(elementConverter);
    }

}
