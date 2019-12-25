package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfStringToJsonConverter extends ListToJsonConverter<String> {

    public ListOfStringToJsonConverter(JsonConverter<String> elementConverter) {
        super(elementConverter);
    }

}
