package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.DayOfWeek;

@Component
public class ListOfDayOfWeekToJsonConverter extends ListToJsonConverter<DayOfWeek> {

    public ListOfDayOfWeekToJsonConverter(JsonConverter<DayOfWeek> elementConverter) {
        super(elementConverter);
    }

}
