package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfIntegerJsonConverter extends ListJsonConverter<Integer> {

    public ListOfIntegerJsonConverter(JsonConverter<Integer> elementConverter) {
        super(elementConverter);
    }

}
