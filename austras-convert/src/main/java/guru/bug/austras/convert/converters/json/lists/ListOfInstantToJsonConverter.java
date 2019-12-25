package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;

import java.time.Instant;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class ListOfInstantToJsonConverter extends ListToJsonConverter<Instant> {

    public ListOfInstantToJsonConverter(JsonConverter<Instant> elementConverter) {
        super(elementConverter);
    }

}
