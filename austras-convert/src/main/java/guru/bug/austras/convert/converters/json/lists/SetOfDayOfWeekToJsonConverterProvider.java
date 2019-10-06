package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.DayOfWeek;

public class SetOfDayOfWeekToJsonConverterProvider extends SetToJsonConverterProvider<DayOfWeek> {

    public SetOfDayOfWeekToJsonConverterProvider(Provider<? extends JsonConverter<DayOfWeek>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
