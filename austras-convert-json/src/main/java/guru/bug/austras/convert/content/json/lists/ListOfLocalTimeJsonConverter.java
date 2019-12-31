package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.LocalTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfLocalTimeJsonConverter extends AbstractListJsonConverter<LocalTime> {

    public ListOfLocalTimeJsonConverter(@ApplicationJson JsonConverter<LocalTime> elementConverter) {
        super(elementConverter);
    }

}
