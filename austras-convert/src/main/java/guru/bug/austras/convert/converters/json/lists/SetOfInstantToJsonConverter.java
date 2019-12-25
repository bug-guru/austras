package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;

import java.time.Instant;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfInstantToJsonConverter extends SetToJsonConverter<Instant> {

    public SetOfInstantToJsonConverter(JsonConverter<Instant> elementConverter) {
        super(elementConverter);
    }

}
