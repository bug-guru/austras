package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.Year;

public class ListOfYearToJsonConverterProvider extends ListToJsonConverterProvider<Year> {

    public ListOfYearToJsonConverterProvider(Provider<JsonConverter<Year>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
