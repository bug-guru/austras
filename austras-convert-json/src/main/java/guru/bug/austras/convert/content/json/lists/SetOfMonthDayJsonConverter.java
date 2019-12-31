package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.MonthDay;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfMonthDayJsonConverter extends AbstractSetJsonConverter<MonthDay> {

    public SetOfMonthDayJsonConverter(@ApplicationJson JsonConverter<MonthDay> elementConverter) {
        super(elementConverter);
    }

}
