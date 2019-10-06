package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.LocalDateTime;

public class SetOfLocalDateTimeToJsonConverterProvider extends SetToJsonConverterProvider<LocalDateTime> {

    public SetOfLocalDateTimeToJsonConverterProvider(Provider<? extends JsonConverter<LocalDateTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
