package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.MonthDay;

public class ListOfMonthDayToJsonConverterProvider extends ListToJsonConverterProvider<MonthDay> {

    public ListOfMonthDayToJsonConverterProvider(Provider<JsonConverter<MonthDay>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
