package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.ZoneOffset;

public class SetOfZoneOffsetToJsonConverterProvider extends SetToJsonConverterProvider<ZoneOffset> {

    public SetOfZoneOffsetToJsonConverterProvider(Provider<JsonConverter<ZoneOffset>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
