package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfLongJsonConverter extends ListJsonConverter<Long> {

    public ListOfLongJsonConverter(JsonConverter<Long> elementConverter) {
        super(elementConverter);
    }

}
