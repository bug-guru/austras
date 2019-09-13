package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.Period;

public class ListOfPeriodToJsonConverterProvider extends ListToJsonConverterProvider<Period> {

    public ListOfPeriodToJsonConverterProvider(Provider<JsonConverter<Period>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
