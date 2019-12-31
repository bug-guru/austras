package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.ZonedDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfZonedDateTimeJsonConverter extends AbstractListJsonConverter<ZonedDateTime> {

    public ListOfZonedDateTimeJsonConverter(@ApplicationJson JsonConverter<ZonedDateTime> elementConverter) {
        super(elementConverter);
    }

}
