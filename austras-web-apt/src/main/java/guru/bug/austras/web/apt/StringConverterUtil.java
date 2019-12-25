package guru.bug.austras.web.apt;

import guru.bug.austras.apt.core.common.model.DependencyModel;
import guru.bug.austras.convert.converters.*;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

class StringConverterUtil {
    private StringConverterUtil() {
    }

    static DependencyModel selectConverter(TypeMirror type) {
        var kind = type.getKind();
        if (kind.isPrimitive()) {
            return selectPrimitiveConverterProvider(type);
        } else if (kind == TypeKind.DECLARED) {
            return selectObjectConverterProvider((DeclaredType) type);
        } else {
            throw new IllegalArgumentException("Type " + type);
        }
    }

    private static DependencyModel selectObjectConverterProvider(DeclaredType type) {
        var element = (TypeElement) type.asElement();
        if (element.getQualifiedName().contentEquals(String.class.getName())) {
            return null;
        }
        var baseClassName = StringConverter.class.getName();
        var typeClassName = element.getQualifiedName().toString();
        var converterType = baseClassName + "<" + typeClassName + ">";
        return DependencyModelUtil.createDependencyModel(converterType);
    }

    private static DependencyModel selectPrimitiveConverterProvider(TypeMirror type) {
        Class<?> converter;
        switch (type.getKind()) {
            case SHORT:
                converter = StringShortConverter.class;
                break;
            case INT:
                converter = StringIntegerConverter.class;
                break;
            case DOUBLE:
                converter = StringDoubleConverter.class;
                break;
            case BOOLEAN:
                converter = StringBooleanConverter.class;
                break;
            case CHAR:
                converter = StringCharacterConverter.class;
                break;
            case FLOAT:
                converter = StringFloatConverter.class;
                break;
            case LONG:
                converter = StringLongConverter.class;
                break;
            case BYTE:
                converter = StringByteConverter.class;
                break;
            default:
                throw new IllegalArgumentException("Type " + type);
        }
        return DependencyModelUtil.createDependencyModel(converter);
    }
}
