package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

import java.math.BigDecimal;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfBigDecimalJsonConverter extends AbstractListJsonConverter<BigDecimal> {

    public ListOfBigDecimalJsonConverter(@ApplicationJson JsonConverter<BigDecimal> elementConverter) {
        super(elementConverter);
    }

}
