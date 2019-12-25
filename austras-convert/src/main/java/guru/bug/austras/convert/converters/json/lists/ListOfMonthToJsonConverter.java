package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;

import java.time.Month;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfMonthToJsonConverter extends ListToJsonConverter<Month> {

    public ListOfMonthToJsonConverter(JsonConverter<Month> elementConverter) {
        super(elementConverter);
    }

}
