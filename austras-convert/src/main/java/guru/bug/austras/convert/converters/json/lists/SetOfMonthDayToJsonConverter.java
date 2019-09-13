package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.MonthDay;

@Component
public class SetOfMonthDayToJsonConverter extends SetToJsonConverter<MonthDay> {

    public SetOfMonthDayToJsonConverter(JsonConverter<MonthDay> elementConverter) {
        super(elementConverter);
    }

}
