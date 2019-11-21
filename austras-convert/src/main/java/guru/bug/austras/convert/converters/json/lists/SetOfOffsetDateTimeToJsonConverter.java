package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.OffsetDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfOffsetDateTimeToJsonConverter extends SetToJsonConverter<OffsetDateTime> {

    public SetOfOffsetDateTimeToJsonConverter(JsonConverter<OffsetDateTime> elementConverter) {
        super(elementConverter);
    }

}
