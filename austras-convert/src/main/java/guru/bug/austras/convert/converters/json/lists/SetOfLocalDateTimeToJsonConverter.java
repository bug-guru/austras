package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.LocalDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfLocalDateTimeToJsonConverter extends SetToJsonConverter<LocalDateTime> {

    public SetOfLocalDateTimeToJsonConverter(JsonConverter<LocalDateTime> elementConverter) {
        super(elementConverter);
    }

}
