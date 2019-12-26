package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Duration;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfDurationJsonConverter extends ListJsonConverter<Duration> {

    public ListOfDurationJsonConverter(JsonConverter<Duration> elementConverter) {
        super(elementConverter);
    }

}
