package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.YearMonth;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfYearMonthToJsonConverter extends SetToJsonConverter<YearMonth> {

    public SetOfYearMonthToJsonConverter(JsonConverter<YearMonth> elementConverter) {
        super(elementConverter);
    }

}
