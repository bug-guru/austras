package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.OffsetDateTime;

public class SetOfOffsetDateTimeToJsonConverterProvider extends SetToJsonConverterProvider<OffsetDateTime> {

    public SetOfOffsetDateTimeToJsonConverterProvider(Provider<JsonConverter<OffsetDateTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
