package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.LocalDate;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfLocalDateJsonConverter extends SetJsonConverter<LocalDate> {

    public SetOfLocalDateJsonConverter(JsonConverter<LocalDate> elementConverter) {
        super(elementConverter);
    }

}
