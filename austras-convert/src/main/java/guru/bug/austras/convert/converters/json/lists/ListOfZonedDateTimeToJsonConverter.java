package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.ZonedDateTime;

@Component
public class ListOfZonedDateTimeToJsonConverter extends ListToJsonConverter<ZonedDateTime> {

    public ListOfZonedDateTimeToJsonConverter(JsonConverter<ZonedDateTime> elementConverter) {
        super(elementConverter);
    }

}
