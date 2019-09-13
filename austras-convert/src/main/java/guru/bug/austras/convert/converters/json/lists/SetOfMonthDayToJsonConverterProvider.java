package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.MonthDay;

public class SetOfMonthDayToJsonConverterProvider extends SetToJsonConverterProvider<MonthDay> {

    public SetOfMonthDayToJsonConverterProvider(Provider<JsonConverter<MonthDay>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
