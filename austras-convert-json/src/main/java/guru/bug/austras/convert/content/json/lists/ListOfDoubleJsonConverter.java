package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfDoubleJsonConverter extends AbstractListJsonConverter<Double> {

    public ListOfDoubleJsonConverter(@ApplicationJson JsonConverter<Double> elementConverter) {
        super(elementConverter);
    }

}
