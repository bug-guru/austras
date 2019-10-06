package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.provider.Provider;

import java.time.Period;

public class SetOfPeriodToJsonConverterProvider extends SetToJsonConverterProvider<Period> {

    public SetOfPeriodToJsonConverterProvider(Provider<? extends JsonConverter<Period>> elementConverterProvider) {
        super(elementConverterProvider);
    }

}
