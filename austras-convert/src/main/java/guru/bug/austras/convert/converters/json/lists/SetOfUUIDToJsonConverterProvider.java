package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.util.UUID;

public class SetOfUUIDToJsonConverterProvider extends SetToJsonConverterProvider<UUID> {

    public SetOfUUIDToJsonConverterProvider(Provider<? extends JsonConverter<UUID>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
