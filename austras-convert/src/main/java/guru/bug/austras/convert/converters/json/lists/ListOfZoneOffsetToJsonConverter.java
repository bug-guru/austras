package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.ZoneOffset;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfZoneOffsetToJsonConverter extends ListToJsonConverter<ZoneOffset> {

    public ListOfZoneOffsetToJsonConverter(JsonConverter<ZoneOffset> elementConverter) {
        super(elementConverter);
    }

}
