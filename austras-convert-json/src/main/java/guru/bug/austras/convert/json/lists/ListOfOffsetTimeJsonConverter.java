package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

import java.time.OffsetTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfOffsetTimeJsonConverter extends AbstractListJsonConverter<OffsetTime> {

    public ListOfOffsetTimeJsonConverter(@ApplicationJson JsonConverter<OffsetTime> elementConverter) {
        super(elementConverter);
    }

}
