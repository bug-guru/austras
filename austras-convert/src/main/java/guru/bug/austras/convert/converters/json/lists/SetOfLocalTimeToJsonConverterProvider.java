package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.LocalTime;

public class SetOfLocalTimeToJsonConverterProvider extends SetToJsonConverterProvider<LocalTime> {

    public SetOfLocalTimeToJsonConverterProvider(Provider<? extends JsonConverter<LocalTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
