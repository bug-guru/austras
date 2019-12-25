package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.Duration;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfDurationToJsonConverter extends ListToJsonConverter<Duration> {

    public ListOfDurationToJsonConverter(JsonConverter<Duration> elementConverter) {
        super(elementConverter);
    }

}
