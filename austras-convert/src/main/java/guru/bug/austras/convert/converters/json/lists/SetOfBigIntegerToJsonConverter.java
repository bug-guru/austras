package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.math.BigInteger;

@Component
public class SetOfBigIntegerToJsonConverter extends SetToJsonConverter<BigInteger> {

    public SetOfBigIntegerToJsonConverter(JsonConverter<BigInteger> elementConverter) {
        super(elementConverter);
    }

}
