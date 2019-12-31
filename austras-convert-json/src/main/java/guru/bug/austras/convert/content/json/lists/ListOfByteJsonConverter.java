package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfByteJsonConverter extends AbstractListJsonConverter<Byte> {

    public ListOfByteJsonConverter(@ApplicationJson JsonConverter<Byte> elementConverter) {
        super(elementConverter);
    }

}
