package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.math.BigInteger;

@ApplicationJson
@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfBigIntegerJsonConverter extends ListJsonConverter<BigInteger> {

    public ListOfBigIntegerJsonConverter(JsonConverter<BigInteger> elementConverter) {
        super(elementConverter);
    }

}
