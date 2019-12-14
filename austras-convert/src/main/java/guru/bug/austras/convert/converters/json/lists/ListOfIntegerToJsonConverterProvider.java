package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfIntegerToJsonConverterProvider extends ListToJsonConverterProvider<Integer> {

    public ListOfIntegerToJsonConverterProvider(Provider<? extends JsonConverter<Integer>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
