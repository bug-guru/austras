package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfFloatToJsonConverterProvider extends ListToJsonConverterProvider<Float> {

    public ListOfFloatToJsonConverterProvider(Provider<? extends JsonConverter<Float>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
