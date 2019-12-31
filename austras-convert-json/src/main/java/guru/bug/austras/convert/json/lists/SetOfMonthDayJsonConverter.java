package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

import java.time.MonthDay;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfMonthDayJsonConverter extends AbstractSetJsonConverter<MonthDay> {

    public SetOfMonthDayJsonConverter(@ApplicationJson JsonConverter<MonthDay> elementConverter) {
        super(elementConverter);
    }

}
