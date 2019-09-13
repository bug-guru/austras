package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.ZoneOffset;

@Component
public class SetOfZoneOffsetToJsonConverter extends SetToJsonConverter<ZoneOffset> {

    public SetOfZoneOffsetToJsonConverter(JsonConverter<ZoneOffset> elementConverter) {
        super(elementConverter);
    }

}
