package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class SetOfDoubleToJsonConverterProvider extends SetToJsonConverterProvider<Double> {

    public SetOfDoubleToJsonConverterProvider(Provider<JsonConverter<Double>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
