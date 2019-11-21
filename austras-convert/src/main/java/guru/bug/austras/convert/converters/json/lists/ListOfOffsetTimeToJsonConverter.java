package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.OffsetTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class ListOfOffsetTimeToJsonConverter extends ListToJsonConverter<OffsetTime> {

    public ListOfOffsetTimeToJsonConverter(JsonConverter<OffsetTime> elementConverter) {
        super(elementConverter);
    }

}
