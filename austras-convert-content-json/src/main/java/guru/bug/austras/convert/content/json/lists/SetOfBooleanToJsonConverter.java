package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfBooleanJsonConverter extends SetJsonConverter<Boolean> {

    public SetOfBooleanJsonConverter(JsonConverter<Boolean> elementConverter) {
        super(elementConverter);
    }

}
