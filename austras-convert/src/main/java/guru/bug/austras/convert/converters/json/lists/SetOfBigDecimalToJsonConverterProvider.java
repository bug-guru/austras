package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.math.BigDecimal;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfBigDecimalToJsonConverterProvider extends SetToJsonConverterProvider<BigDecimal> {

    public SetOfBigDecimalToJsonConverterProvider(Provider<? extends JsonConverter<BigDecimal>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
