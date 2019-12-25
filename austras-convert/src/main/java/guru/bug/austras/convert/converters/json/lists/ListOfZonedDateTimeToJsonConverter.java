package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;

import java.time.ZonedDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfZonedDateTimeToJsonConverter extends ListToJsonConverter<ZonedDateTime> {

    public ListOfZonedDateTimeToJsonConverter(JsonConverter<ZonedDateTime> elementConverter) {
        super(elementConverter);
    }

}
