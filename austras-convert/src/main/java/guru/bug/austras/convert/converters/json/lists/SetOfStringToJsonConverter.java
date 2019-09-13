package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@Component
public class SetOfStringToJsonConverter extends SetToJsonConverter<String> {

    public SetOfStringToJsonConverter(JsonConverter<String> elementConverter) {
        super(elementConverter);
    }

}
