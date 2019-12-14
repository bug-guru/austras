package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;

import java.time.OffsetTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfOffsetTimeToJsonConverterProvider extends SetToJsonConverterProvider<OffsetTime> {

    public SetOfOffsetTimeToJsonConverterProvider(Provider<? extends JsonConverter<OffsetTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
