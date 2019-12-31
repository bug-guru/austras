package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfIntegerJsonConverter extends AbstractListJsonConverter<Integer> {

    public ListOfIntegerJsonConverter(@ApplicationJson JsonConverter<Integer> elementConverter) {
        super(elementConverter);
    }

}
