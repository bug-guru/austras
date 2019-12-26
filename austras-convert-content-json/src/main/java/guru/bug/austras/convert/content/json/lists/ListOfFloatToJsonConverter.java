package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfFloatJsonConverter extends ListJsonConverter<Float> {

    public ListOfFloatJsonConverter(JsonConverter<Float> elementConverter) {
        super(elementConverter);
    }

}
