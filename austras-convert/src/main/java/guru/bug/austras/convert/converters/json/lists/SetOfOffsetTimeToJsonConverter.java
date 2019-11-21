package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.OffsetTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfOffsetTimeToJsonConverter extends SetToJsonConverter<OffsetTime> {

    public SetOfOffsetTimeToJsonConverter(JsonConverter<OffsetTime> elementConverter) {
        super(elementConverter);
    }

}
