package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.util.UUID;

public class ListOfUUIDToJsonConverterProvider extends ListToJsonConverterProvider<UUID> {

    public ListOfUUIDToJsonConverterProvider(Provider<JsonConverter<UUID>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
