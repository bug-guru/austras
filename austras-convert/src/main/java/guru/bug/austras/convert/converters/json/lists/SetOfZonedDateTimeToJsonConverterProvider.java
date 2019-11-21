package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.ZonedDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfZonedDateTimeToJsonConverterProvider extends SetToJsonConverterProvider<ZonedDateTime> {

    public SetOfZonedDateTimeToJsonConverterProvider(Provider<? extends JsonConverter<ZonedDateTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
