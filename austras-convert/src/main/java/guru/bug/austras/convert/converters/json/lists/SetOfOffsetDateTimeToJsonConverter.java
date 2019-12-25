package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.OffsetDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfOffsetDateTimeToJsonConverter extends SetToJsonConverter<OffsetDateTime> {

    public SetOfOffsetDateTimeToJsonConverter(JsonConverter<OffsetDateTime> elementConverter) {
        super(elementConverter);
    }

}
