package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfDoubleJsonConverter extends SetJsonConverter<Double> {

    public SetOfDoubleJsonConverter(JsonConverter<Double> elementConverter) {
        super(elementConverter);
    }

}
