package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

import java.time.ZoneId;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfZoneIdJsonConverter extends AbstractSetJsonConverter<ZoneId> {

    public SetOfZoneIdJsonConverter(@ApplicationJson JsonConverter<ZoneId> elementConverter) {
        super(elementConverter);
    }

}
