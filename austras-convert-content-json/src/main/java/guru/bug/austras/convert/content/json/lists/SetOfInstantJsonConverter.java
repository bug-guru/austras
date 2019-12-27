package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Instant;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfInstantJsonConverter extends AbstractSetJsonConverter<Instant> {

    public SetOfInstantJsonConverter(@ApplicationJson JsonConverter<Instant> elementConverter) {
        super(elementConverter);
    }

}
