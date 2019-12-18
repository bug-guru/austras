package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.time.ZoneOffset;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfZoneOffsetToJsonConverterProvider extends SetToJsonConverterProvider<ZoneOffset> {

    public SetOfZoneOffsetToJsonConverterProvider(Provider<? extends JsonConverter<ZoneOffset>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
