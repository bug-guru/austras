package guru.bug.austras.web.apt;

import guru.bug.austras.apt.core.common.model.DependencyModel;
import guru.bug.austras.apt.core.common.model.QualifierSetModel;
import guru.bug.austras.apt.core.common.model.WrappingType;

class DependencyModelUtil {
    private DependencyModelUtil() {
    }

    static DependencyModel createDependencyModel(Class<?> primitiveConverter) {
        return createDependencyModel(primitiveConverter.getName());
    }

    static DependencyModel createDependencyModel(String type) {
        return DependencyModel.builder()
                .wrapping(WrappingType.NONE)
                .type(type)
                .qualifiers(QualifierSetModel.ofDefault())
                .build();
    }

}
