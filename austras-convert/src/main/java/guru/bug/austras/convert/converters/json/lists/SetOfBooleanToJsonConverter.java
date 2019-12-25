package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfBooleanToJsonConverter extends SetToJsonConverter<Boolean> {

    public SetOfBooleanToJsonConverter(JsonConverter<Boolean> elementConverter) {
        super(elementConverter);
    }

}
