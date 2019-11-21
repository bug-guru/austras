package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.ZoneOffset;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfZoneOffsetToJsonConverterProvider extends SetToJsonConverterProvider<ZoneOffset> {

    public SetOfZoneOffsetToJsonConverterProvider(Provider<? extends JsonConverter<ZoneOffset>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
