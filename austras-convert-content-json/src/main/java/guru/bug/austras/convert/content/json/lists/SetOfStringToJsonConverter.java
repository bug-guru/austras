package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfStringJsonConverter extends SetJsonConverter<String> {

    public SetOfStringJsonConverter(JsonConverter<String> elementConverter) {
        super(elementConverter);
    }

}
