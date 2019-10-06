package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class ListOfShortToJsonConverterProvider extends ListToJsonConverterProvider<Short> {

    public ListOfShortToJsonConverterProvider(Provider<? extends JsonConverter<Short>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
