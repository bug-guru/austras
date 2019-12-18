package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.time.ZoneId;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfZoneIdToJsonConverterProvider extends SetToJsonConverterProvider<ZoneId> {

    public SetOfZoneIdToJsonConverterProvider(Provider<? extends JsonConverter<ZoneId>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
