package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.LocalDate;

@Component
public class ListOfLocalDateToJsonConverter extends ListToJsonConverter<LocalDate> {

    public ListOfLocalDateToJsonConverter(JsonConverter<LocalDate> elementConverter) {
        super(elementConverter);
    }

}
