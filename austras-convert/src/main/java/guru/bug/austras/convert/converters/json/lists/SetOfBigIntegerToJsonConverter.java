package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;

import java.math.BigInteger;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfBigIntegerToJsonConverter extends SetToJsonConverter<BigInteger> {

    public SetOfBigIntegerToJsonConverter(JsonConverter<BigInteger> elementConverter) {
        super(elementConverter);
    }

}
