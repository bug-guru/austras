package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.MonthDay;

@Component
public class ListOfMonthDayToJsonConverter extends ListToJsonConverter<MonthDay> {

    public ListOfMonthDayToJsonConverter(JsonConverter<MonthDay> elementConverter) {
        super(elementConverter);
    }

}
