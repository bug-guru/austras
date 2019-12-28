package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.DayOfWeek;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfDayOfWeekJsonConverter extends AbstractListJsonConverter<DayOfWeek> {

    public ListOfDayOfWeekJsonConverter(@ApplicationJson JsonConverter<DayOfWeek> elementConverter) {
        super(elementConverter);
    }

}