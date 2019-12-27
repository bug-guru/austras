package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.DayOfWeek;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfDayOfWeekJsonConverter extends AbstractSetJsonConverter<DayOfWeek> {

    public SetOfDayOfWeekJsonConverter(@ApplicationJson JsonConverter<DayOfWeek> elementConverter) {
        super(elementConverter);
    }

}
