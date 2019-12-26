package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfUUIDJsonConverter extends SetJsonConverter<UUID> {

    public SetOfUUIDJsonConverter(JsonConverter<UUID> elementConverter) {
        super(elementConverter);
    }

}
