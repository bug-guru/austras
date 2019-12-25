package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;

import java.time.Year;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfYearToJsonConverter extends ListToJsonConverter<Year> {

    public ListOfYearToJsonConverter(JsonConverter<Year> elementConverter) {
        super(elementConverter);
    }

}
