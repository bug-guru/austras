package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.OffsetDateTime;

public class ListOfOffsetDateTimeToJsonConverterProvider extends ListToJsonConverterProvider<OffsetDateTime> {

    public ListOfOffsetDateTimeToJsonConverterProvider(Provider<? extends JsonConverter<OffsetDateTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
