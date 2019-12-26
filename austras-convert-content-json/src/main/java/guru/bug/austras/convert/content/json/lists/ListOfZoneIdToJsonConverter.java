package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.ZoneId;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfZoneIdJsonConverter extends ListJsonConverter<ZoneId> {

    public ListOfZoneIdJsonConverter(JsonConverter<ZoneId> elementConverter) {
        super(elementConverter);
    }

}
