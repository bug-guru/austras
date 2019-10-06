package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class ListOfFloatToJsonConverterProvider extends ListToJsonConverterProvider<Float> {

    public ListOfFloatToJsonConverterProvider(Provider<? extends JsonConverter<Float>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
