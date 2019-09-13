package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.ZoneId;

@Component
public class ListOfZoneIdToJsonConverter extends ListToJsonConverter<ZoneId> {

    public ListOfZoneIdToJsonConverter(JsonConverter<ZoneId> elementConverter) {
        super(elementConverter);
    }

}
