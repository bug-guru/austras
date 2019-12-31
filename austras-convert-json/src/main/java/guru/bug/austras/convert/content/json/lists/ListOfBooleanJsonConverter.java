package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfBooleanJsonConverter extends AbstractListJsonConverter<Boolean> {

    public ListOfBooleanJsonConverter(@ApplicationJson JsonConverter<Boolean> elementConverter) {
        super(elementConverter);
    }

}
