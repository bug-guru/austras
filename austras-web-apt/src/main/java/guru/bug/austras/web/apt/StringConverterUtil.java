package guru.bug.austras.web.apt;

import guru.bug.austras.apt.core.common.model.DependencyModel;
import guru.bug.austras.convert.content.*;
import guru.bug.austras.convert.content.plaintext.ShortPlainTextConverter;

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
        var baseClassName = ContentConverter.class.getName();
        var typeClassName = element.getQualifiedName().toString();
        var converterType = baseClassName + "<" + typeClassName + ">";
        return DependencyModelUtil.createDependencyModel(converterType);
    }

    private static DependencyModel selectPrimitiveConverterProvider(TypeMirror type) {
        Class<?> converter;
        switch (type.getKind()) {
            case SHORT:
                converter = ShortPlainTextConverter.class;
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
