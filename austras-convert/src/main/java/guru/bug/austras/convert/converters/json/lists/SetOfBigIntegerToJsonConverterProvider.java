package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.math.BigInteger;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfBigIntegerToJsonConverterProvider extends SetToJsonConverterProvider<BigInteger> {

    public SetOfBigIntegerToJsonConverterProvider(Provider<? extends JsonConverter<BigInteger>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
