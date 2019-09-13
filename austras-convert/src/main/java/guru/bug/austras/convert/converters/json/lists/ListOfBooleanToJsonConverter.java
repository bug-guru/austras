package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@Component
public class ListOfBooleanToJsonConverter extends ListToJsonConverter<Boolean> {

    public ListOfBooleanToJsonConverter(JsonConverter<Boolean> elementConverter) {
        super(elementConverter);
    }

}
