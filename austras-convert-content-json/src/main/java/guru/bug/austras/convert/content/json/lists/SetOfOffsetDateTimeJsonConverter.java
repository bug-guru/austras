package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.OffsetDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfOffsetDateTimeJsonConverter extends AbstractSetJsonConverter<OffsetDateTime> {

    public SetOfOffsetDateTimeJsonConverter(@ApplicationJson JsonConverter<OffsetDateTime> elementConverter) {
        super(elementConverter);
    }

}