package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfUUIDJsonConverter extends AbstractListJsonConverter<UUID> {

    public ListOfUUIDJsonConverter(@ApplicationJson JsonConverter<UUID> elementConverter) {
        super(elementConverter);
    }

}
