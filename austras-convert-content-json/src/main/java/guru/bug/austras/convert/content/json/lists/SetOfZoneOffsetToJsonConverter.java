package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.ZoneOffset;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfZoneOffsetJsonConverter extends SetJsonConverter<ZoneOffset> {

    public SetOfZoneOffsetJsonConverter(JsonConverter<ZoneOffset> elementConverter) {
        super(elementConverter);
    }

}
