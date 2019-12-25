package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfUUIDToJsonConverter extends ListToJsonConverter<UUID> {

    public ListOfUUIDToJsonConverter(JsonConverter<UUID> elementConverter) {
        super(elementConverter);
    }

}
