package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfUUIDJsonConverter extends AbstractSetJsonConverter<UUID> {

    public SetOfUUIDJsonConverter(@ApplicationJson JsonConverter<UUID> elementConverter) {
        super(elementConverter);
    }

}
