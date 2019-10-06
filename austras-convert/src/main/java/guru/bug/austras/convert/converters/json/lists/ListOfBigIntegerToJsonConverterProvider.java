package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.math.BigInteger;

public class ListOfBigIntegerToJsonConverterProvider extends ListToJsonConverterProvider<BigInteger> {

    public ListOfBigIntegerToJsonConverterProvider(Provider<? extends JsonConverter<BigInteger>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
