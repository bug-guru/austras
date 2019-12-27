package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfShortJsonConverter extends AbstractListJsonConverter<Short> {

    public ListOfShortJsonConverter(@ApplicationJson JsonConverter<Short> elementConverter) {
        super(elementConverter);
    }

}
