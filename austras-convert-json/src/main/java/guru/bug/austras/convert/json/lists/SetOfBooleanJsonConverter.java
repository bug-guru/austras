package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfBooleanJsonConverter extends AbstractSetJsonConverter<Boolean> {

    public SetOfBooleanJsonConverter(@ApplicationJson JsonConverter<Boolean> elementConverter) {
        super(elementConverter);
    }

}
