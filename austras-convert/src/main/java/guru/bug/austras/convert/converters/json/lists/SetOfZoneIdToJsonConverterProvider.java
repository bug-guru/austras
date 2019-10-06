package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.ZoneId;

public class SetOfZoneIdToJsonConverterProvider extends SetToJsonConverterProvider<ZoneId> {

    public SetOfZoneIdToJsonConverterProvider(Provider<? extends JsonConverter<ZoneId>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
