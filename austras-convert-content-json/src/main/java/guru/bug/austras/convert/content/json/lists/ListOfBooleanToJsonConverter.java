package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfBooleanJsonConverter extends ListJsonConverter<Boolean> {

    public ListOfBooleanJsonConverter(JsonConverter<Boolean> elementConverter) {
        super(elementConverter);
    }

}
