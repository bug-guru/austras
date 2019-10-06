package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class ListOfDoubleToJsonConverterProvider extends ListToJsonConverterProvider<Double> {

    public ListOfDoubleToJsonConverterProvider(Provider<? extends JsonConverter<Double>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
