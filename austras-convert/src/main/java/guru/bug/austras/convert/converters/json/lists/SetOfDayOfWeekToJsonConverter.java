package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.DayOfWeek;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfDayOfWeekToJsonConverter extends SetToJsonConverter<DayOfWeek> {

    public SetOfDayOfWeekToJsonConverter(JsonConverter<DayOfWeek> elementConverter) {
        super(elementConverter);
    }

}
