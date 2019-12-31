package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfShortJsonConverter extends AbstractSetJsonConverter<Short> {

    public SetOfShortJsonConverter(@ApplicationJson JsonConverter<Short> elementConverter) {
        super(elementConverter);
    }

}
