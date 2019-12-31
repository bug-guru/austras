package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.math.BigInteger;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfBigIntegerJsonConverter extends AbstractSetJsonConverter<BigInteger> {

    public SetOfBigIntegerJsonConverter(@ApplicationJson JsonConverter<BigInteger> elementConverter) {
        super(elementConverter);
    }

}
