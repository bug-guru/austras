package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Year;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfYearJsonConverter extends SetJsonConverter<Year> {

    public SetOfYearJsonConverter(JsonConverter<Year> elementConverter) {
        super(elementConverter);
    }

}
