package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfStringJsonConverter extends AbstractListJsonConverter<String> {

    public ListOfStringJsonConverter(@ApplicationJson JsonConverter<String> elementConverter) {
        super(elementConverter);
    }

}
