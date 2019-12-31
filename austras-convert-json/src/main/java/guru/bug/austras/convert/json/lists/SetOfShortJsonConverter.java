package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfShortJsonConverter extends AbstractSetJsonConverter<Short> {

    public SetOfShortJsonConverter(@ApplicationJson JsonConverter<Short> elementConverter) {
        super(elementConverter);
    }

}
