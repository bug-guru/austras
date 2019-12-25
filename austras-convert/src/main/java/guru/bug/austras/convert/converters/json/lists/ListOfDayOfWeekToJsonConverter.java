package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;

import java.time.DayOfWeek;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfDayOfWeekToJsonConverter extends ListToJsonConverter<DayOfWeek> {

    public ListOfDayOfWeekToJsonConverter(JsonConverter<DayOfWeek> elementConverter) {
        super(elementConverter);
    }

}
