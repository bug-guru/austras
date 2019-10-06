package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.OffsetTime;

public class ListOfOffsetTimeToJsonConverterProvider extends ListToJsonConverterProvider<OffsetTime> {

    public ListOfOffsetTimeToJsonConverterProvider(Provider<? extends JsonConverter<OffsetTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
