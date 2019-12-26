package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Month;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfMonthJsonConverter extends SetJsonConverter<Month> {

    public SetOfMonthJsonConverter(JsonConverter<Month> elementConverter) {
        super(elementConverter);
    }

}
