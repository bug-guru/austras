package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfIntegerJsonConverter extends AbstractSetJsonConverter<Integer> {

    public SetOfIntegerJsonConverter(@ApplicationJson JsonConverter<Integer> elementConverter) {
        super(elementConverter);
    }

}
