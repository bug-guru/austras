package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;

import java.math.BigDecimal;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfBigDecimalToJsonConverterProvider extends ListToJsonConverterProvider<BigDecimal> {

    public ListOfBigDecimalToJsonConverterProvider(Provider<? extends JsonConverter<BigDecimal>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
