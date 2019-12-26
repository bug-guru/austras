package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.ZoneId;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfZoneIdToJsonConverter extends ListToJsonConverter<ZoneId> {

    public ListOfZoneIdToJsonConverter(JsonConverter<ZoneId> elementConverter) {
        super(elementConverter);
    }

}
