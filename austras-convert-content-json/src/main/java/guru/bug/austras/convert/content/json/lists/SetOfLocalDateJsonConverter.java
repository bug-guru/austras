package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.LocalDate;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfLocalDateJsonConverter extends AbstractSetJsonConverter<LocalDate> {

    public SetOfLocalDateJsonConverter(@ApplicationJson JsonConverter<LocalDate> elementConverter) {
        super(elementConverter);
    }

}
