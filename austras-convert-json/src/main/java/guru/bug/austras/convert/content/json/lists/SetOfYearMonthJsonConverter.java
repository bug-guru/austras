package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.YearMonth;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfYearMonthJsonConverter extends AbstractSetJsonConverter<YearMonth> {

    public SetOfYearMonthJsonConverter(@ApplicationJson JsonConverter<YearMonth> elementConverter) {
        super(elementConverter);
    }

}
