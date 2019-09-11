package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

public class SetToJsonConverterProvider<T> implements Provider<SetToJsonConverter<T>> {
    private final SetToJsonConverter<T> instance;

    public SetToJsonConverterProvider(Provider<JsonConverter<T>> elementConverterProvider) {
        instance = new SetToJsonConverter<>(elementConverterProvider.get());
    }

    @Override
    public SetToJsonConverter<T> get() {
        return instance;
    }
}
