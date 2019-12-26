package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Year;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfYearJsonConverter extends ListJsonConverter<Year> {

    public ListOfYearJsonConverter(JsonConverter<Year> elementConverter) {
        super(elementConverter);
    }

}
