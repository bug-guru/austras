package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@Component
public class SetOfLongToJsonConverter extends SetToJsonConverter<Long> {

    public SetOfLongToJsonConverter(JsonConverter<Long> elementConverter) {
        super(elementConverter);
    }

}
