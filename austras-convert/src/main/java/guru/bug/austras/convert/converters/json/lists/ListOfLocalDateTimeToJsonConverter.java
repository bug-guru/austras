package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.LocalDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class ListOfLocalDateTimeToJsonConverter extends ListToJsonConverter<LocalDateTime> {

    public ListOfLocalDateTimeToJsonConverter(JsonConverter<LocalDateTime> elementConverter) {
        super(elementConverter);
    }

}
