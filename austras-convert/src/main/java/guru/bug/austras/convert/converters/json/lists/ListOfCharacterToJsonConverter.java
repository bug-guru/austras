package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@Component
public class ListOfCharacterToJsonConverter extends ListToJsonConverter<Character> {

    public ListOfCharacterToJsonConverter(JsonConverter<Character> elementConverter) {
        super(elementConverter);
    }

}