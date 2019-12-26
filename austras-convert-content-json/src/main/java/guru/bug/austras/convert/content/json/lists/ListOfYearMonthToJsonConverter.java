package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.YearMonth;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfYearMonthJsonConverter extends ListJsonConverter<YearMonth> {

    public ListOfYearMonthJsonConverter(JsonConverter<YearMonth> elementConverter) {
        super(elementConverter);
    }

}
