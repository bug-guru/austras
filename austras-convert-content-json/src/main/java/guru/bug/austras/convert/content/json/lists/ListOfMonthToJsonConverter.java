package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Month;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfMonthJsonConverter extends ListJsonConverter<Month> {

    public ListOfMonthJsonConverter(JsonConverter<Month> elementConverter) {
        super(elementConverter);
    }

}
