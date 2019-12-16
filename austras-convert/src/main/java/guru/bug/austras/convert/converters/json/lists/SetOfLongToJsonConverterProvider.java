package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfLongToJsonConverterProvider extends SetToJsonConverterProvider<Long> {

    public SetOfLongToJsonConverterProvider(Provider<? extends JsonConverter<Long>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
