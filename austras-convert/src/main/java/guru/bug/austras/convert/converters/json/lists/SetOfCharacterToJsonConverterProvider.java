package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class SetOfCharacterToJsonConverterProvider extends SetToJsonConverterProvider<Character> {

    public SetOfCharacterToJsonConverterProvider(Provider<? extends JsonConverter<Character>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
