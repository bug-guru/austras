package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@Component
public class ListOfByteToJsonConverter extends ListToJsonConverter<Byte> {

    public ListOfByteToJsonConverter(JsonConverter<Byte> elementConverter) {
        super(elementConverter);
    }

}
