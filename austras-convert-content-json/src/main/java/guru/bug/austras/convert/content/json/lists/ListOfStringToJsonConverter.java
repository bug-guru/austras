package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfStringJsonConverter extends ListJsonConverter<String> {

    public ListOfStringJsonConverter(JsonConverter<String> elementConverter) {
        super(elementConverter);
    }

}
