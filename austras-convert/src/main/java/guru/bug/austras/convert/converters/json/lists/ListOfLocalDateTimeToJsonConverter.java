package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;

import java.time.LocalDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfLocalDateTimeToJsonConverter extends ListToJsonConverter<LocalDateTime> {

    public ListOfLocalDateTimeToJsonConverter(JsonConverter<LocalDateTime> elementConverter) {
        super(elementConverter);
    }

}
