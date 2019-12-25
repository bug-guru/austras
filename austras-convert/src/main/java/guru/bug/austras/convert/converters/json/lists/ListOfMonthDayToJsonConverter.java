package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.MonthDay;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfMonthDayToJsonConverter extends ListToJsonConverter<MonthDay> {

    public ListOfMonthDayToJsonConverter(JsonConverter<MonthDay> elementConverter) {
        super(elementConverter);
    }

}
