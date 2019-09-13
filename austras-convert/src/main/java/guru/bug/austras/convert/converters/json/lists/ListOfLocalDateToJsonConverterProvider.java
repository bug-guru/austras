package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.LocalDate;

public class ListOfLocalDateToJsonConverterProvider extends ListToJsonConverterProvider<LocalDate> {

    public ListOfLocalDateToJsonConverterProvider(Provider<JsonConverter<LocalDate>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
