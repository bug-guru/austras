package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.math.BigInteger;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfBigIntegerJsonConverter extends AbstractListJsonConverter<BigInteger> {

    public ListOfBigIntegerJsonConverter(@ApplicationJson JsonConverter<BigInteger> elementConverter) {
        super(elementConverter);
    }

}
