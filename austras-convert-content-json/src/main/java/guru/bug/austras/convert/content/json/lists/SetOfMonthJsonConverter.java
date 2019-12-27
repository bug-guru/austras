package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Month;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfMonthJsonConverter extends AbstractSetJsonConverter<Month> {

    public SetOfMonthJsonConverter(@ApplicationJson JsonConverter<Month> elementConverter) {
        super(elementConverter);
    }

}
