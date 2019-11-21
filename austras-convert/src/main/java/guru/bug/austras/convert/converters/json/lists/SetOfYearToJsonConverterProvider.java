package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.Year;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfYearToJsonConverterProvider extends SetToJsonConverterProvider<Year> {

    public SetOfYearToJsonConverterProvider(Provider<? extends JsonConverter<Year>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
