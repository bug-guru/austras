package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;

import java.time.Period;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfPeriodToJsonConverterProvider extends SetToJsonConverterProvider<Period> {

    public SetOfPeriodToJsonConverterProvider(Provider<? extends JsonConverter<Period>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
