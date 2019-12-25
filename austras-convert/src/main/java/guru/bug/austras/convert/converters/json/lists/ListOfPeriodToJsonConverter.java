package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;

import java.time.Period;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfPeriodToJsonConverter extends ListToJsonConverter<Period> {

    public ListOfPeriodToJsonConverter(JsonConverter<Period> elementConverter) {
        super(elementConverter);
    }

}
