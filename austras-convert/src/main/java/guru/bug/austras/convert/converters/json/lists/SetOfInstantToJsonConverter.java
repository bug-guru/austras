package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.Instant;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfInstantToJsonConverter extends SetToJsonConverter<Instant> {

    public SetOfInstantToJsonConverter(JsonConverter<Instant> elementConverter) {
        super(elementConverter);
    }

}
