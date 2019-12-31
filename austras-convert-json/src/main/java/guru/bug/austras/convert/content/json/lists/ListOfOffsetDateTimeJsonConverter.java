package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.OffsetDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfOffsetDateTimeJsonConverter extends AbstractListJsonConverter<OffsetDateTime> {

    public ListOfOffsetDateTimeJsonConverter(@ApplicationJson JsonConverter<OffsetDateTime> elementConverter) {
        super(elementConverter);
    }

}
