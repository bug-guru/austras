package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfFloatToJsonConverter extends SetToJsonConverter<Float> {

    public SetOfFloatToJsonConverter(JsonConverter<Float> elementConverter) {
        super(elementConverter);
    }

}
