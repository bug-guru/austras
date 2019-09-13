package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class ListOfIntegerToJsonConverterProvider extends ListToJsonConverterProvider<Integer> {

    public ListOfIntegerToJsonConverterProvider(Provider<JsonConverter<Integer>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
