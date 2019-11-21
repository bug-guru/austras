package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfByteToJsonConverter extends SetToJsonConverter<Byte> {

    public SetOfByteToJsonConverter(JsonConverter<Byte> elementConverter) {
        super(elementConverter);
    }

}
