package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.ZoneOffset;

public class ListOfZoneOffsetToJsonConverterProvider extends ListToJsonConverterProvider<ZoneOffset> {

    public ListOfZoneOffsetToJsonConverterProvider(Provider<JsonConverter<ZoneOffset>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
