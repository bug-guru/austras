package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfFloatJsonConverter extends AbstractListJsonConverter<Float> {

    public ListOfFloatJsonConverter(@ApplicationJson JsonConverter<Float> elementConverter) {
        super(elementConverter);
    }

}
