package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfShortToJsonConverter extends SetToJsonConverter<Short> {

    public SetOfShortToJsonConverter(JsonConverter<Short> elementConverter) {
        super(elementConverter);
    }

}
