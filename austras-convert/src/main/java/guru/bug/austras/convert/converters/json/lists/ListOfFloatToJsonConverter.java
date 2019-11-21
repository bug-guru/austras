package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class ListOfFloatToJsonConverter extends ListToJsonConverter<Float> {

    public ListOfFloatToJsonConverter(JsonConverter<Float> elementConverter) {
        super(elementConverter);
    }

}
