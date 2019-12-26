package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfShortJsonConverter extends SetJsonConverter<Short> {

    public SetOfShortJsonConverter(JsonConverter<Short> elementConverter) {
        super(elementConverter);
    }

}
