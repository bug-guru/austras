package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.YearMonth;

public class ListOfYearMonthToJsonConverterProvider extends ListToJsonConverterProvider<YearMonth> {

    public ListOfYearMonthToJsonConverterProvider(Provider<JsonConverter<YearMonth>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
