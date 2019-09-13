package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@Component
public class ListOfShortToJsonConverter extends ListToJsonConverter<Short> {

    public ListOfShortToJsonConverter(JsonConverter<Short> elementConverter) {
        super(elementConverter);
    }

}
