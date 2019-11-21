package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfBooleanToJsonConverter extends SetToJsonConverter<Boolean> {

    public SetOfBooleanToJsonConverter(JsonConverter<Boolean> elementConverter) {
        super(elementConverter);
    }

}
