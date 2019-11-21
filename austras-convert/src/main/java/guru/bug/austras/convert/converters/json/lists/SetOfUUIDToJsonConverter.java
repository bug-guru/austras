package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfUUIDToJsonConverter extends SetToJsonConverter<UUID> {

    public SetOfUUIDToJsonConverter(JsonConverter<UUID> elementConverter) {
        super(elementConverter);
    }

}
