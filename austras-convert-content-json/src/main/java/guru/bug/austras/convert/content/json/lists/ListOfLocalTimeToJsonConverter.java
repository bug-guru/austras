package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.LocalTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfLocalTimeJsonConverter extends ListJsonConverter<LocalTime> {

    public ListOfLocalTimeJsonConverter(JsonConverter<LocalTime> elementConverter) {
        super(elementConverter);
    }

}
