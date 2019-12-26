package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfLongJsonConverter extends SetJsonConverter<Long> {

    public SetOfLongJsonConverter(JsonConverter<Long> elementConverter) {
        super(elementConverter);
    }

}
