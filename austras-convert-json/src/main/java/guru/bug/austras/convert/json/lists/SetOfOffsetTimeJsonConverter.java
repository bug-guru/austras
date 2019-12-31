package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

import java.time.OffsetTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfOffsetTimeJsonConverter extends AbstractSetJsonConverter<OffsetTime> {

    public SetOfOffsetTimeJsonConverter(@ApplicationJson JsonConverter<OffsetTime> elementConverter) {
        super(elementConverter);
    }

}
