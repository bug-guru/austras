package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class ListOfBooleanToJsonConverterProvider extends ListToJsonConverterProvider<Boolean> {

    public ListOfBooleanToJsonConverterProvider(Provider<JsonConverter<Boolean>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
