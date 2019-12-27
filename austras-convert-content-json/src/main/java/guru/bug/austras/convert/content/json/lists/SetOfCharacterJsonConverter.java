package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfCharacterJsonConverter extends AbstractSetJsonConverter<Character> {

    public SetOfCharacterJsonConverter(@ApplicationJson JsonConverter<Character> elementConverter) {
        super(elementConverter);
    }

}
