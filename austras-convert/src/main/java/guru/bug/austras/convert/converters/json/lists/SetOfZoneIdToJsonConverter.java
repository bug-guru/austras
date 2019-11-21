package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.ZoneId;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfZoneIdToJsonConverter extends SetToJsonConverter<ZoneId> {

    public SetOfZoneIdToJsonConverter(JsonConverter<ZoneId> elementConverter) {
        super(elementConverter);
    }

}
