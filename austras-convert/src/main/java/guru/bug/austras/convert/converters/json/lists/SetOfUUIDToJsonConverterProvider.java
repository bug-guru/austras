package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;

import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfUUIDToJsonConverterProvider extends SetToJsonConverterProvider<UUID> {

    public SetOfUUIDToJsonConverterProvider(Provider<? extends JsonConverter<UUID>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
