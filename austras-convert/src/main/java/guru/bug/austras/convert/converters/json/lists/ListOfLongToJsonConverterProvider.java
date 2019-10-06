package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class ListOfLongToJsonConverterProvider extends ListToJsonConverterProvider<Long> {

    public ListOfLongToJsonConverterProvider(Provider<? extends JsonConverter<Long>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
