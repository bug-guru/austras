package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;

import java.time.LocalTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfLocalTimeToJsonConverter extends ListToJsonConverter<LocalTime> {

    public ListOfLocalTimeToJsonConverter(JsonConverter<LocalTime> elementConverter) {
        super(elementConverter);
    }

}
