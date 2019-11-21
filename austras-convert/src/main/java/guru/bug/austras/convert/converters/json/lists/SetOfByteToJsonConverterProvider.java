package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfByteToJsonConverterProvider extends SetToJsonConverterProvider<Byte> {

    public SetOfByteToJsonConverterProvider(Provider<? extends JsonConverter<Byte>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
