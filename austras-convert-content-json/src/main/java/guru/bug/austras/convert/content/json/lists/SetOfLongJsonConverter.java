package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfLongJsonConverter extends AbstractSetJsonConverter<Long> {

    public SetOfLongJsonConverter(@ApplicationJson JsonConverter<Long> elementConverter) {
        super(elementConverter);
    }

}
