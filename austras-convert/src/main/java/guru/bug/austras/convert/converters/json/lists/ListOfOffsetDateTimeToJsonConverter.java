package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.OffsetDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfOffsetDateTimeToJsonConverter extends ListToJsonConverter<OffsetDateTime> {

    public ListOfOffsetDateTimeToJsonConverter(JsonConverter<OffsetDateTime> elementConverter) {
        super(elementConverter);
    }

}
