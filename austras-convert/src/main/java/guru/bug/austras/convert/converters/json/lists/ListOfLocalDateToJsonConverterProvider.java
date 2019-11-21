package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.LocalDate;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfLocalDateToJsonConverterProvider extends ListToJsonConverterProvider<LocalDate> {

    public ListOfLocalDateToJsonConverterProvider(Provider<? extends JsonConverter<LocalDate>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
