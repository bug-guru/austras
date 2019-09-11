package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class ListToJsonConverterProvider<T> implements Provider<ListToJsonConverter<T>> {
    private final ListToJsonConverter<T> instance;

    public ListToJsonConverterProvider(Provider<JsonConverter<T>> elementConverterProvider) {
        instance = new ListToJsonConverter<>(elementConverterProvider.get());
    }

    @Override
    public ListToJsonConverter<T> get() {
        return instance;
    }
}
