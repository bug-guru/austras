package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class ListOfLongToJsonConverter extends ListToJsonConverter<Long> {

    public ListOfLongToJsonConverter(JsonConverter<Long> elementConverter) {
        super(elementConverter);
    }

}
