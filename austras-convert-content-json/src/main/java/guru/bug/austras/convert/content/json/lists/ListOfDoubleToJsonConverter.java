package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfDoubleJsonConverter extends ListJsonConverter<Double> {

    public ListOfDoubleJsonConverter(JsonConverter<Double> elementConverter) {
        super(elementConverter);
    }

}
