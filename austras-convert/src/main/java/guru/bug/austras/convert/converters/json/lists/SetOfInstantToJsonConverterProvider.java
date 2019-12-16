package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.time.Instant;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfInstantToJsonConverterProvider extends SetToJsonConverterProvider<Instant> {

    public SetOfInstantToJsonConverterProvider(Provider<? extends JsonConverter<Instant>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
