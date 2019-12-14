package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;

import java.time.LocalDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfLocalDateTimeToJsonConverterProvider extends SetToJsonConverterProvider<LocalDateTime> {

    public SetOfLocalDateTimeToJsonConverterProvider(Provider<? extends JsonConverter<LocalDateTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
