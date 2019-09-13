package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class SetOfShortToJsonConverterProvider extends SetToJsonConverterProvider<Short> {

    public SetOfShortToJsonConverterProvider(Provider<JsonConverter<Short>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
