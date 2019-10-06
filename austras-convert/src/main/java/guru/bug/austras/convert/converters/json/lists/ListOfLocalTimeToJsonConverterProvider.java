package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.LocalTime;

public class ListOfLocalTimeToJsonConverterProvider extends ListToJsonConverterProvider<LocalTime> {

    public ListOfLocalTimeToJsonConverterProvider(Provider<? extends JsonConverter<LocalTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
