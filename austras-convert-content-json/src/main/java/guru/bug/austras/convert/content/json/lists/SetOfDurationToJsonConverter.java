package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Duration;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfDurationJsonConverter extends SetJsonConverter<Duration> {

    public SetOfDurationJsonConverter(JsonConverter<Duration> elementConverter) {
        super(elementConverter);
    }

}
