package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.math.BigInteger;

@Default
@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfBigIntegerToJsonConverter extends ListToJsonConverter<BigInteger> {

    public ListOfBigIntegerToJsonConverter(JsonConverter<BigInteger> elementConverter) {
        super(elementConverter);
    }

}
