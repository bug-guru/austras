package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.OffsetTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfOffsetTimeToJsonConverter extends ListToJsonConverter<OffsetTime> {

    public ListOfOffsetTimeToJsonConverter(JsonConverter<OffsetTime> elementConverter) {
        super(elementConverter);
    }

}
