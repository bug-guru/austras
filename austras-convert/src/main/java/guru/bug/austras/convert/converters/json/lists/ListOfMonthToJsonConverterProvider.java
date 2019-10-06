package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.Month;

public class ListOfMonthToJsonConverterProvider extends ListToJsonConverterProvider<Month> {

    public ListOfMonthToJsonConverterProvider(Provider<? extends JsonConverter<Month>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
