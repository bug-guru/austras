package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.Year;

@Component
public class ListOfYearToJsonConverter extends ListToJsonConverter<Year> {

    public ListOfYearToJsonConverter(JsonConverter<Year> elementConverter) {
        super(elementConverter);
    }

}
