package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.Duration;

public class ListOfDurationToJsonConverterProvider extends ListToJsonConverterProvider<Duration> {

    public ListOfDurationToJsonConverterProvider(Provider<JsonConverter<Duration>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
