package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.YearMonth;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfYearMonthJsonConverter extends SetJsonConverter<YearMonth> {

    public SetOfYearMonthJsonConverter(JsonConverter<YearMonth> elementConverter) {
        super(elementConverter);
    }

}
