package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.MonthDay;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfMonthDayToJsonConverterProvider extends ListToJsonConverterProvider<MonthDay> {

    public ListOfMonthDayToJsonConverterProvider(Provider<? extends JsonConverter<MonthDay>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
