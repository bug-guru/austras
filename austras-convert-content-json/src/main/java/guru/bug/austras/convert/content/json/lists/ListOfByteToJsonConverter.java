package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfByteJsonConverter extends ListJsonConverter<Byte> {

    public ListOfByteJsonConverter(JsonConverter<Byte> elementConverter) {
        super(elementConverter);
    }

}
