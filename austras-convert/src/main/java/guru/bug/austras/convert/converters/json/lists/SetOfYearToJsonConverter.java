package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.Year;

@Component
public class SetOfYearToJsonConverter extends SetToJsonConverter<Year> {

    public SetOfYearToJsonConverter(JsonConverter<Year> elementConverter) {
        super(elementConverter);
    }

}
