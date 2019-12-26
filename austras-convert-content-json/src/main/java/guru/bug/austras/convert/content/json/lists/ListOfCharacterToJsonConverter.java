package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfCharacterJsonConverter extends ListJsonConverter<Character> {

    public ListOfCharacterJsonConverter(JsonConverter<Character> elementConverter) {
        super(elementConverter);
    }

}
