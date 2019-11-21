package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public abstract class SetToJsonConverterProvider<T> implements Provider<SetToJsonConverter<T>> {
    private final SetToJsonConverter<T> instance;

    public SetToJsonConverterProvider(Provider<? extends JsonConverter<T>> elementConverterProvider) {
        instance = new SetToJsonConverter<>(elementConverterProvider.get());
    }

    @Override
    public SetToJsonConverter<T> get() {
        return instance;
    }
}
