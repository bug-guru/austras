package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public abstract class ListToJsonConverterProvider<T> implements Provider<ListToJsonConverter<T>> {
    private final ListToJsonConverter<T> instance;

    public ListToJsonConverterProvider(Provider<? extends JsonConverter<T>> elementConverterProvider) {
        instance = new ListToJsonConverter<>(elementConverterProvider.get());
    }

    @Override
    public ListToJsonConverter<T> get() {
        return instance;
    }
}
