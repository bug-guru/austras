package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.Period;

@Component
public class SetOfPeriodToJsonConverter extends SetToJsonConverter<Period> {

    public SetOfPeriodToJsonConverter(JsonConverter<Period> elementConverter) {
        super(elementConverter);
    }

}
