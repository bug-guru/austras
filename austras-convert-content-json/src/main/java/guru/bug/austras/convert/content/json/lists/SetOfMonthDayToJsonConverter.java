package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.MonthDay;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfMonthDayJsonConverter extends SetJsonConverter<MonthDay> {

    public SetOfMonthDayJsonConverter(JsonConverter<MonthDay> elementConverter) {
        super(elementConverter);
    }

}
