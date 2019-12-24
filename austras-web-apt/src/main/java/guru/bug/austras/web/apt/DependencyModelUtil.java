package guru.bug.austras.web.apt;

import guru.bug.austras.apt.core.model.DependencyModel;
import org.apache.commons.lang3.StringUtils;

class DependencyModelUtil {
    private DependencyModelUtil() {
    }

    static DependencyModel createDependencyModel(Class<?> primitiveConverter) {
        return createDependencyModel(primitiveConverter.getName(), StringUtils.uncapitalize(primitiveConverter.getSimpleName()));
    }

    static DependencyModel createDependencyModel(String type, String name) {
        var result = new DependencyModel(type, qualifiers, wrapping);
        result.setCollection(false);
        result.setProvider(false);
        result.setName(name);
        result.setType(type);
        result.setQualifiers(null); // TODO think of taking qualifiers from the method's param
        return result;
    }

}
