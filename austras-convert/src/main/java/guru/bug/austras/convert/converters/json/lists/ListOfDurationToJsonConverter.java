package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.Duration;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class ListOfDurationToJsonConverter extends ListToJsonConverter<Duration> {

    public ListOfDurationToJsonConverter(JsonConverter<Duration> elementConverter) {
        super(elementConverter);
    }

}
