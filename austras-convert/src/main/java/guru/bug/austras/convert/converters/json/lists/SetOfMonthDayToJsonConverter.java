package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.MonthDay;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfMonthDayToJsonConverter extends SetToJsonConverter<MonthDay> {

    public SetOfMonthDayToJsonConverter(JsonConverter<MonthDay> elementConverter) {
        super(elementConverter);
    }

}
