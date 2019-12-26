package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.math.BigInteger;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfBigIntegerJsonConverter extends SetJsonConverter<BigInteger> {

    public SetOfBigIntegerJsonConverter(JsonConverter<BigInteger> elementConverter) {
        super(elementConverter);
    }

}
