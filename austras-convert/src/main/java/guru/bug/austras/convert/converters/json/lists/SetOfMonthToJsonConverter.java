package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.Month;

@Component
public class SetOfMonthToJsonConverter extends SetToJsonConverter<Month> {

    public SetOfMonthToJsonConverter(JsonConverter<Month> elementConverter) {
        super(elementConverter);
    }

}
