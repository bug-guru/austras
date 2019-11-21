package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfCharacterToJsonConverter extends SetToJsonConverter<Character> {

    public SetOfCharacterToJsonConverter(JsonConverter<Character> elementConverter) {
        super(elementConverter);
    }

}
