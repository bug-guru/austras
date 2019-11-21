package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfIntegerToJsonConverter extends SetToJsonConverter<Integer> {

    public SetOfIntegerToJsonConverter(JsonConverter<Integer> elementConverter) {
        super(elementConverter);
    }

}
