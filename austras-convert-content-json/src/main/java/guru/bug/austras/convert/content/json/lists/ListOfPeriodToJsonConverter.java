package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Period;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfPeriodJsonConverter extends ListJsonConverter<Period> {

    public ListOfPeriodJsonConverter(JsonConverter<Period> elementConverter) {
        super(elementConverter);
    }

}
