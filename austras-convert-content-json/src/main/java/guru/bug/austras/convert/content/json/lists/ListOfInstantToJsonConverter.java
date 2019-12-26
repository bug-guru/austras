package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Instant;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfInstantJsonConverter extends ListJsonConverter<Instant> {

    public ListOfInstantJsonConverter(JsonConverter<Instant> elementConverter) {
        super(elementConverter);
    }

}
