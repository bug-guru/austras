package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Year;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfYearJsonConverter extends AbstractSetJsonConverter<Year> {

    public SetOfYearJsonConverter(@ApplicationJson JsonConverter<Year> elementConverter) {
        super(elementConverter);
    }

}
