package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.YearMonth;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class ListOfYearMonthToJsonConverter extends ListToJsonConverter<YearMonth> {

    public ListOfYearMonthToJsonConverter(JsonConverter<YearMonth> elementConverter) {
        super(elementConverter);
    }

}
