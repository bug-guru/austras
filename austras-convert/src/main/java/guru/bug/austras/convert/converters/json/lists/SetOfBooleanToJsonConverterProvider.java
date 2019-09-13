package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class SetOfBooleanToJsonConverterProvider extends SetToJsonConverterProvider<Boolean> {

    public SetOfBooleanToJsonConverterProvider(Provider<JsonConverter<Boolean>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
