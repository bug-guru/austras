package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class SetOfStringToJsonConverterProvider extends SetToJsonConverterProvider<String> {

    public SetOfStringToJsonConverterProvider(Provider<? extends JsonConverter<String>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
