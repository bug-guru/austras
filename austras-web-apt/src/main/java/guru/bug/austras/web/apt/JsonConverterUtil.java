package guru.bug.austras.web.apt;

import guru.bug.austras.apt.core.model.DependencyModel;
import guru.bug.austras.convert.converters.*;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

class JsonConverterUtil {
    private JsonConverterUtil() {
    }

    static DependencyModel selectConverter(TypeMirror type) {
        if (type.getKind().isPrimitive()) {
            return selectPrimitiveJsonConverter(type);
        } else if (type.getKind() == TypeKind.DECLARED) {
            return selectObjectJsonConverter((DeclaredType) type);
        } else {
            throw new IllegalArgumentException("Type " + type);
        }

    }

    private static DependencyModel selectObjectJsonConverter(DeclaredType type) {
        var element = (TypeElement) type.asElement();
        var baseClassName = JsonConverter.class.getName();
        var typeClassName = element.getQualifiedName().toString();
        var typeClassSimpleName = element.getSimpleName().toString();
        var converterType = baseClassName + "<" + typeClassName + ">";
        var name = "jsonConverter" + typeClassSimpleName;
        return DependencyModelUtil.createDependencyModel(converterType, name);
    }

    private static DependencyModel selectPrimitiveJsonConverter(TypeMirror type) {
        Class<?> converter;
        switch (type.getKind()) {
            case SHORT:
                converter = JsonShortConverter.class;
                break;
            case INT:
                converter = JsonIntegerConverter.class;
                break;
            case DOUBLE:
                converter = JsonDoubleConverter.class;
                break;
            case BOOLEAN:
                converter = JsonBooleanConverter.class;
                break;
            case CHAR:
                converter = JsonCharacterConverter.class;
                break;
            case FLOAT:
                converter = JsonFloatConverter.class;
                break;
            case LONG:
                converter = JsonLongConverter.class;
                break;
            case BYTE:
                converter = JsonByteConverter.class;
                break;
            default:
                throw new IllegalArgumentException("Type " + type);
        }
        return DependencyModelUtil.createDependencyModel(converter);
    }
}
