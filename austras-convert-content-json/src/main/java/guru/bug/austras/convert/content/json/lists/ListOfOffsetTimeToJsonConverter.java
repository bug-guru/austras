package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.OffsetTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfOffsetTimeJsonConverter extends ListJsonConverter<OffsetTime> {

    public ListOfOffsetTimeJsonConverter(JsonConverter<OffsetTime> elementConverter) {
        super(elementConverter);
    }

}
