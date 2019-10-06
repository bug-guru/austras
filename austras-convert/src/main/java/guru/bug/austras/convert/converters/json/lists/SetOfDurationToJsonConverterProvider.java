package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.Duration;

public class SetOfDurationToJsonConverterProvider extends SetToJsonConverterProvider<Duration> {

    public SetOfDurationToJsonConverterProvider(Provider<? extends JsonConverter<Duration>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
