package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class ListOfCharacterToJsonConverterProvider extends ListToJsonConverterProvider<Character> {

    public ListOfCharacterToJsonConverterProvider(Provider<JsonConverter<Character>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
