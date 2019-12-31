package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.ZoneOffset;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfZoneOffsetJsonConverter extends AbstractListJsonConverter<ZoneOffset> {

    public ListOfZoneOffsetJsonConverter(@ApplicationJson JsonConverter<ZoneOffset> elementConverter) {
        super(elementConverter);
    }

}
