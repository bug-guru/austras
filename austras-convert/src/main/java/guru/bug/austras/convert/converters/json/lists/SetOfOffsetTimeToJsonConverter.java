package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;

import java.time.OffsetTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfOffsetTimeToJsonConverter extends SetToJsonConverter<OffsetTime> {

    public SetOfOffsetTimeToJsonConverter(JsonConverter<OffsetTime> elementConverter) {
        super(elementConverter);
    }

}
