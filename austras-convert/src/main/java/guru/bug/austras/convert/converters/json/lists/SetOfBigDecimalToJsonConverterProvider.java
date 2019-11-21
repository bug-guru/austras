package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.math.BigDecimal;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfBigDecimalToJsonConverterProvider extends SetToJsonConverterProvider<BigDecimal> {

    public SetOfBigDecimalToJsonConverterProvider(Provider<? extends JsonConverter<BigDecimal>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
