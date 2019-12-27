package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfCharacterJsonConverter extends AbstractListJsonConverter<Character> {

    public ListOfCharacterJsonConverter(@ApplicationJson JsonConverter<Character> elementConverter) {
        super(elementConverter);
    }

}
