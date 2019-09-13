package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.LocalDate;

public class SetOfLocalDateToJsonConverterProvider extends SetToJsonConverterProvider<LocalDate> {

    public SetOfLocalDateToJsonConverterProvider(Provider<JsonConverter<LocalDate>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
