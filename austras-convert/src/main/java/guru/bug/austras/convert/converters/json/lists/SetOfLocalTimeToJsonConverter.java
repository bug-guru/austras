package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.LocalTime;

@Component
public class SetOfLocalTimeToJsonConverter extends SetToJsonConverter<LocalTime> {

    public SetOfLocalTimeToJsonConverter(JsonConverter<LocalTime> elementConverter) {
        super(elementConverter);
    }

}
