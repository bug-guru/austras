package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfDoubleToJsonConverterProvider extends SetToJsonConverterProvider<Double> {

    public SetOfDoubleToJsonConverterProvider(Provider<? extends JsonConverter<Double>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
