package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfIntegerJsonConverter extends SetJsonConverter<Integer> {

    public SetOfIntegerJsonConverter(JsonConverter<Integer> elementConverter) {
        super(elementConverter);
    }

}
