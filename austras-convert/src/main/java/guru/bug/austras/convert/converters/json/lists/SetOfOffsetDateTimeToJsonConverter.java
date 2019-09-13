package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.OffsetDateTime;

@Component
public class SetOfOffsetDateTimeToJsonConverter extends SetToJsonConverter<OffsetDateTime> {

    public SetOfOffsetDateTimeToJsonConverter(JsonConverter<OffsetDateTime> elementConverter) {
        super(elementConverter);
    }

}
