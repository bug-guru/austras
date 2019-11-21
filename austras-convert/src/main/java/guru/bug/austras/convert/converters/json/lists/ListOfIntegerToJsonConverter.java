package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class ListOfIntegerToJsonConverter extends ListToJsonConverter<Integer> {

    public ListOfIntegerToJsonConverter(JsonConverter<Integer> elementConverter) {
        super(elementConverter);
    }

}
