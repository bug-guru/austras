package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfStringJsonConverter extends AbstractSetJsonConverter<String> {

    public SetOfStringJsonConverter(@ApplicationJson JsonConverter<String> elementConverter) {
        super(elementConverter);
    }

}
