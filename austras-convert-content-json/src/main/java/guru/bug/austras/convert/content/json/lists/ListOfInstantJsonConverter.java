package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Instant;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfInstantJsonConverter extends AbstractListJsonConverter<Instant> {

    public ListOfInstantJsonConverter(@ApplicationJson JsonConverter<Instant> elementConverter) {
        super(elementConverter);
    }

}