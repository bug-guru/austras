package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfShortToJsonConverter extends SetToJsonConverter<Short> {

    public SetOfShortToJsonConverter(JsonConverter<Short> elementConverter) {
        super(elementConverter);
    }

}
