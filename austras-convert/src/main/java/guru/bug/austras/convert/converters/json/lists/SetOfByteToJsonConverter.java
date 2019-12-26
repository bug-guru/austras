package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfByteToJsonConverter extends SetToJsonConverter<Byte> {

    public SetOfByteToJsonConverter(JsonConverter<Byte> elementConverter) {
        super(elementConverter);
    }

}
