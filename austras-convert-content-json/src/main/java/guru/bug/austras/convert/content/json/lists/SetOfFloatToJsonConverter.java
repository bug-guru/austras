package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfFloatJsonConverter extends SetJsonConverter<Float> {

    public SetOfFloatJsonConverter(JsonConverter<Float> elementConverter) {
        super(elementConverter);
    }

}
