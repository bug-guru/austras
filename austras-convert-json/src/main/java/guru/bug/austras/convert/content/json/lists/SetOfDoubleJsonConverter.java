package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfDoubleJsonConverter extends AbstractSetJsonConverter<Double> {

    public SetOfDoubleJsonConverter(@ApplicationJson JsonConverter<Double> elementConverter) {
        super(elementConverter);
    }

}
