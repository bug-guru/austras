package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfShortJsonConverter extends ListJsonConverter<Short> {

    public ListOfShortJsonConverter(JsonConverter<Short> elementConverter) {
        super(elementConverter);
    }

}
