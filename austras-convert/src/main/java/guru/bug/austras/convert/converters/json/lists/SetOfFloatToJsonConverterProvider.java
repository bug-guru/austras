package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class SetOfFloatToJsonConverterProvider extends SetToJsonConverterProvider<Float> {

    public SetOfFloatToJsonConverterProvider(Provider<JsonConverter<Float>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}