package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfShortToJsonConverterProvider extends SetToJsonConverterProvider<Short> {

    public SetOfShortToJsonConverterProvider(Provider<? extends JsonConverter<Short>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
