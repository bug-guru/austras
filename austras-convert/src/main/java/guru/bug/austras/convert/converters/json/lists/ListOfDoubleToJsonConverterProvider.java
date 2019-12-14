package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfDoubleToJsonConverterProvider extends ListToJsonConverterProvider<Double> {

    public ListOfDoubleToJsonConverterProvider(Provider<? extends JsonConverter<Double>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
