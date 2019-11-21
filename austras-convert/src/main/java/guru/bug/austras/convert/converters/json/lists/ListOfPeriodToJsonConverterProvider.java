package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.Period;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfPeriodToJsonConverterProvider extends ListToJsonConverterProvider<Period> {

    public ListOfPeriodToJsonConverterProvider(Provider<? extends JsonConverter<Period>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
