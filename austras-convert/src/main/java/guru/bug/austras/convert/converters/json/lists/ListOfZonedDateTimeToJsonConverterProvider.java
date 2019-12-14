package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;

import java.time.ZonedDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfZonedDateTimeToJsonConverterProvider extends ListToJsonConverterProvider<ZonedDateTime> {

    public ListOfZonedDateTimeToJsonConverterProvider(Provider<? extends JsonConverter<ZonedDateTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
