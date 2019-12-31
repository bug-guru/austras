package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.LocalDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfLocalDateTimeJsonConverter extends AbstractSetJsonConverter<LocalDateTime> {

    public SetOfLocalDateTimeJsonConverter(@ApplicationJson JsonConverter<LocalDateTime> elementConverter) {
        super(elementConverter);
    }

}
