package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;

import java.time.Duration;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfDurationToJsonConverter extends SetToJsonConverter<Duration> {

    public SetOfDurationToJsonConverter(JsonConverter<Duration> elementConverter) {
        super(elementConverter);
    }

}
