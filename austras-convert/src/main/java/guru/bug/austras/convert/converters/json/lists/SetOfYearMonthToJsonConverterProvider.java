package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.YearMonth;

public class SetOfYearMonthToJsonConverterProvider extends SetToJsonConverterProvider<YearMonth> {

    public SetOfYearMonthToJsonConverterProvider(Provider<JsonConverter<YearMonth>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
