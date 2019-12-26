package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Instant;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfInstantJsonConverter extends SetJsonConverter<Instant> {

    public SetOfInstantJsonConverter(JsonConverter<Instant> elementConverter) {
        super(elementConverter);
    }

}
