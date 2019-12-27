package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfIntegerJsonConverter extends AbstractListJsonConverter<Integer> {

    public ListOfIntegerJsonConverter(@ApplicationJson JsonConverter<Integer> elementConverter) {
        super(elementConverter);
    }

}
