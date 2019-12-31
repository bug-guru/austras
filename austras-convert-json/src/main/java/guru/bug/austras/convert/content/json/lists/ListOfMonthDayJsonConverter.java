package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.MonthDay;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfMonthDayJsonConverter extends AbstractListJsonConverter<MonthDay> {

    public ListOfMonthDayJsonConverter(@ApplicationJson JsonConverter<MonthDay> elementConverter) {
        super(elementConverter);
    }

}
