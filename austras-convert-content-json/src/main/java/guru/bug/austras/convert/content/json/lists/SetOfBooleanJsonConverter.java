package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfBooleanJsonConverter extends AbstractSetJsonConverter<Boolean> {

    public SetOfBooleanJsonConverter(@ApplicationJson JsonConverter<Boolean> elementConverter) {
        super(elementConverter);
    }

}
