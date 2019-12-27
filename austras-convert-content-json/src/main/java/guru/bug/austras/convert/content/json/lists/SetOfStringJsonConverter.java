package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfStringJsonConverter extends AbstractSetJsonConverter<String> {

    public SetOfStringJsonConverter(@ApplicationJson JsonConverter<String> elementConverter) {
        super(elementConverter);
    }

}
