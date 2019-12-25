package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.ZonedDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfZonedDateTimeToJsonConverter extends SetToJsonConverter<ZonedDateTime> {

    public SetOfZonedDateTimeToJsonConverter(JsonConverter<ZonedDateTime> elementConverter) {
        super(elementConverter);
    }

}
