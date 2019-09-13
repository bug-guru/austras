package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.ZoneOffset;

@Component
public class ListOfZoneOffsetToJsonConverter extends ListToJsonConverter<ZoneOffset> {

    public ListOfZoneOffsetToJsonConverter(JsonConverter<ZoneOffset> elementConverter) {
        super(elementConverter);
    }

}
