package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfLongJsonConverter extends AbstractListJsonConverter<Long> {

    public ListOfLongJsonConverter(@ApplicationJson JsonConverter<Long> elementConverter) {
        super(elementConverter);
    }

}
