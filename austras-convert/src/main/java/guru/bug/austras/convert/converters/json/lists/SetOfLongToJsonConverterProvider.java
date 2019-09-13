package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class SetOfLongToJsonConverterProvider extends SetToJsonConverterProvider<Long> {

    public SetOfLongToJsonConverterProvider(Provider<JsonConverter<Long>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}