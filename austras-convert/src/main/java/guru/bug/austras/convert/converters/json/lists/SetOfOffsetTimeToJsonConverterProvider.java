package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.OffsetTime;

public class SetOfOffsetTimeToJsonConverterProvider extends SetToJsonConverterProvider<OffsetTime> {

    public SetOfOffsetTimeToJsonConverterProvider(Provider<? extends JsonConverter<OffsetTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
