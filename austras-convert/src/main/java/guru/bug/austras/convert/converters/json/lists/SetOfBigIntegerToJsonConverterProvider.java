package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.math.BigInteger;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfBigIntegerToJsonConverterProvider extends SetToJsonConverterProvider<BigInteger> {

    public SetOfBigIntegerToJsonConverterProvider(Provider<? extends JsonConverter<BigInteger>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
