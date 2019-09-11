package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.math.BigDecimal;

public class ListOfBigDecimalToJsonConverterProvider extends ListToJsonConverterProvider<BigDecimal> {

    public ListOfBigDecimalToJsonConverterProvider(Provider<JsonConverter<BigDecimal>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
