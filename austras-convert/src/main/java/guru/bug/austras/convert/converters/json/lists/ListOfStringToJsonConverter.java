package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfStringToJsonConverter extends ListToJsonConverter<String> {

    public ListOfStringToJsonConverter(JsonConverter<String> elementConverter) {
        super(elementConverter);
    }

}
