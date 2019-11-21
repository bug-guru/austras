package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.DayOfWeek;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfDayOfWeekToJsonConverter extends SetToJsonConverter<DayOfWeek> {

    public SetOfDayOfWeekToJsonConverter(JsonConverter<DayOfWeek> elementConverter) {
        super(elementConverter);
    }

}
