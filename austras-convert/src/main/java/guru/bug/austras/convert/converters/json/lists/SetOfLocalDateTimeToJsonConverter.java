package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.LocalDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfLocalDateTimeToJsonConverter extends SetToJsonConverter<LocalDateTime> {

    public SetOfLocalDateTimeToJsonConverter(JsonConverter<LocalDateTime> elementConverter) {
        super(elementConverter);
    }

}
