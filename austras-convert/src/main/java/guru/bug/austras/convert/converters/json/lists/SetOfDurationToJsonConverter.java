package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.Duration;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfDurationToJsonConverter extends SetToJsonConverter<Duration> {

    public SetOfDurationToJsonConverter(JsonConverter<Duration> elementConverter) {
        super(elementConverter);
    }

}
