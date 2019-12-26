package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.MonthDay;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfMonthDayJsonConverter extends ListJsonConverter<MonthDay> {

    public ListOfMonthDayJsonConverter(JsonConverter<MonthDay> elementConverter) {
        super(elementConverter);
    }

}
