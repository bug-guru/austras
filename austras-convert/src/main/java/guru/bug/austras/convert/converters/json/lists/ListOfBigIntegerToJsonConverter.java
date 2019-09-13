package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.math.BigInteger;

@Component
public class ListOfBigIntegerToJsonConverter extends ListToJsonConverter<BigInteger> {

    public ListOfBigIntegerToJsonConverter(JsonConverter<BigInteger> elementConverter) {
        super(elementConverter);
    }

}
