package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.LocalDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfLocalDateTimeToJsonConverterProvider extends ListToJsonConverterProvider<LocalDateTime> {

    public ListOfLocalDateTimeToJsonConverterProvider(Provider<? extends JsonConverter<LocalDateTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
