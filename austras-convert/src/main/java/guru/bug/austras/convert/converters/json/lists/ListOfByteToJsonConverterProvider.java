package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfByteToJsonConverterProvider extends ListToJsonConverterProvider<Byte> {

    public ListOfByteToJsonConverterProvider(Provider<? extends JsonConverter<Byte>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
