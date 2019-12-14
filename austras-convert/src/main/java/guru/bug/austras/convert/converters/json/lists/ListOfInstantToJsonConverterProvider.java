package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;

import java.time.Instant;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfInstantToJsonConverterProvider extends ListToJsonConverterProvider<Instant> {

    public ListOfInstantToJsonConverterProvider(Provider<? extends JsonConverter<Instant>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
