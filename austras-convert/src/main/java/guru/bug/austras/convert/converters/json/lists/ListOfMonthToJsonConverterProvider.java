package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;

import java.time.Month;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfMonthToJsonConverterProvider extends ListToJsonConverterProvider<Month> {

    public ListOfMonthToJsonConverterProvider(Provider<? extends JsonConverter<Month>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
