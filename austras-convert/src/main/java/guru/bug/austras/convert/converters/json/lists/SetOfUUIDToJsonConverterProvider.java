package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfUUIDToJsonConverterProvider extends SetToJsonConverterProvider<UUID> {

    public SetOfUUIDToJsonConverterProvider(Provider<? extends JsonConverter<UUID>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
