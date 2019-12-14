package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfCharacterToJsonConverterProvider extends SetToJsonConverterProvider<Character> {

    public SetOfCharacterToJsonConverterProvider(Provider<? extends JsonConverter<Character>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
