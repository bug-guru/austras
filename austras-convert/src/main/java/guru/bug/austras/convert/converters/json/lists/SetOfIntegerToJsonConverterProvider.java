package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class SetOfIntegerToJsonConverterProvider extends SetToJsonConverterProvider<Integer> {

    public SetOfIntegerToJsonConverterProvider(Provider<? extends JsonConverter<Integer>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
