package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class SetOfByteToJsonConverterProvider extends SetToJsonConverterProvider<Byte> {

    public SetOfByteToJsonConverterProvider(Provider<JsonConverter<Byte>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
