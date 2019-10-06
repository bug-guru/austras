package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.Instant;

public class SetOfInstantToJsonConverterProvider extends SetToJsonConverterProvider<Instant> {

    public SetOfInstantToJsonConverterProvider(Provider<? extends JsonConverter<Instant>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
