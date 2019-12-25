package guru.bug.austras.web.apt;

import guru.bug.austras.apt.core.common.model.DependencyModel;
import guru.bug.austras.core.Selector;
import guru.bug.austras.web.contentconverter.*;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

class JsonConverterUtil {
    private JsonConverterUtil() {
    }

    static DependencyModel selectConverter(TypeMirror type) {
        if (type.getKind().isPrimitive()) {
            return selectPrimitiveContentConverter(type);
        } else if (type.getKind() == TypeKind.DECLARED) {
            return selectObjectContentConverter((DeclaredType) type);
        } else {
            throw new IllegalArgumentException("Type " + type);
        }

    }

    private static DependencyModel selectObjectContentConverter(DeclaredType type) {
        var element = (TypeElement) type.asElement();
        var baseClassName = ContentConverter.class.getName();
        var typeClassName = element.getQualifiedName().toString();
        var converterType = Selector.class.getName() + "<" + baseClassName + "<" + typeClassName + ">>";
        return DependencyModelUtil.createDependencyModel(converterType);
    }

    private static DependencyModel selectPrimitiveContentConverter(TypeMirror type) {
        Class<?> converter;
        switch (type.getKind()) {
            case SHORT:
                converter = ShortContentConverter.class;
                break;
            case INT:
                converter = IntegerContentConverter.class;
                break;
            case DOUBLE:
                converter = DoubleContentConverter.class;
                break;
            case BOOLEAN:
                converter = BooleanContentConverter.class;
                break;
            case CHAR:
                converter = CharacterContentConverter.class;
                break;
            case FLOAT:
                converter = FloatContentConverter.class;
                break;
            case LONG:
                converter = LongContentConverter.class;
                break;
            case BYTE:
                converter = ByteContentConverter.class;
                break;
            default:
                throw new IllegalArgumentException("Type " + type);
        }
        return DependencyModelUtil.createDependencyModel(converter);
    }
}
