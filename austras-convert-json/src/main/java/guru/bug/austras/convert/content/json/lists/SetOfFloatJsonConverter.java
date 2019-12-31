package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfFloatJsonConverter extends AbstractSetJsonConverter<Float> {

    public SetOfFloatJsonConverter(@ApplicationJson JsonConverter<Float> elementConverter) {
        super(elementConverter);
    }

}
