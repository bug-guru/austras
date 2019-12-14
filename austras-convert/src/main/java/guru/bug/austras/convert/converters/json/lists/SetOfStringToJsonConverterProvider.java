package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfStringToJsonConverterProvider extends SetToJsonConverterProvider<String> {

    public SetOfStringToJsonConverterProvider(Provider<? extends JsonConverter<String>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
