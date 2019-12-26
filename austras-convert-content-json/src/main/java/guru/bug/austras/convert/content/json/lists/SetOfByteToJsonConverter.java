package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfByteJsonConverter extends SetJsonConverter<Byte> {

    public SetOfByteJsonConverter(JsonConverter<Byte> elementConverter) {
        super(elementConverter);
    }

}
