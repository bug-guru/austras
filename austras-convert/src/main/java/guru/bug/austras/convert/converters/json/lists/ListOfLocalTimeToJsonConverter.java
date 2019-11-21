package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.LocalTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class ListOfLocalTimeToJsonConverter extends ListToJsonConverter<LocalTime> {

    public ListOfLocalTimeToJsonConverter(JsonConverter<LocalTime> elementConverter) {
        super(elementConverter);
    }

}
