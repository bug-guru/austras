package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.time.OffsetDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfOffsetDateTimeToJsonConverterProvider extends SetToJsonConverterProvider<OffsetDateTime> {

    public SetOfOffsetDateTimeToJsonConverterProvider(Provider<? extends JsonConverter<OffsetDateTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
