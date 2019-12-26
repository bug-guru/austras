package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfCharacterJsonConverter extends SetJsonConverter<Character> {

    public SetOfCharacterJsonConverter(JsonConverter<Character> elementConverter) {
        super(elementConverter);
    }

}
