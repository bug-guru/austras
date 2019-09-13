package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.Month;

public class SetOfMonthToJsonConverterProvider extends SetToJsonConverterProvider<Month> {

    public SetOfMonthToJsonConverterProvider(Provider<JsonConverter<Month>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
