/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.web.apt;

import guru.bug.austras.apt.core.common.model.DependencyModel;
import guru.bug.austras.apt.core.common.model.QualifierModel;
import guru.bug.austras.apt.core.common.model.QualifierPropertyModel;
import guru.bug.austras.apt.core.common.model.QualifierSetModel;
import guru.bug.austras.common.model.WrappingType;
import guru.bug.austras.convert.*;
import guru.bug.austras.convert.plaintext.PlainText;
import guru.bug.austras.web.apt.model.DependencyRef;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

class ContentConverterUtil {
    private static final QualifierSetModel PLAIN_TEXT_QUALIFIERS = QualifierSetModel.of(
            QualifierModel.of(Converts.QUALIFIER_NAME,
                    QualifierPropertyModel.of(Converts.PROPERTY_TYPE, PlainText.CONTENT_TYPE)));

    private ContentConverterUtil() {
    }

    static DependencyRef createStringConverterDependency(TypeMirror type) {
        if (type.toString().equals(String.class.getName())) {
            return null;
        }
        var componentTypeAndName = selectConverterTypeAndName(type);
        var dep = DependencyModel.builder()
                .wrapping(WrappingType.NONE)
                .qualifiers(PLAIN_TEXT_QUALIFIERS)
                .type(componentTypeAndName.type)
                .build();
        return new DependencyRef("text" + StringUtils.capitalize(componentTypeAndName.name), dep);
    }

    static DependencyRef createConverterSelectorDependency(TypeMirror type) {
        var componentTypeAndName = selectConverterTypeAndName(type);
        var dep = DependencyModel.builder()
                .wrapping(WrappingType.SELECTOR)
                .qualifiers(QualifierSetModel.ofAny())
                .type(componentTypeAndName.type)
                .build();
        return new DependencyRef(componentTypeAndName.name + "Selector", dep);
    }

    private static TypeAndName selectConverterTypeAndName(TypeMirror type) {
        if (type.getKind().isPrimitive()) {
            return selectPrimitiveContentConverter(type);
        } else if (type.getKind() == TypeKind.DECLARED) {
            return selectObjectContentConverter((DeclaredType) type);
        } else {
            throw new IllegalArgumentException("Type " + type);
        }
    }

    private static TypeAndName selectObjectContentConverter(DeclaredType type) {
        var element = (TypeElement) type.asElement();
        var baseClassName = ContentConverter.class.getName();
        var typeClassName = element.getQualifiedName().toString();
        var className = baseClassName + "<" + typeClassName + ">";
        var varName = StringUtils.uncapitalize(extractSimpleName(typeClassName)) + "Converter";
        return new TypeAndName(className, varName);
    }

    private static String extractSimpleName(String typeClassName) {
        var idx = typeClassName.lastIndexOf('.');
        if (idx == -1) {
            return typeClassName;
        } else {
            return typeClassName.substring(idx + 1);
        }
    }

    private static TypeAndName selectPrimitiveContentConverter(TypeMirror type) {
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
        var name = "primitive" + StringUtils.capitalize(type.toString()) + "Converter";
        return new TypeAndName(converter.getName(), name);
    }

    private static class TypeAndName {
        private final String type;
        private final String name;

        public TypeAndName(String type, String name) {
            this.type = type;
            this.name = name;
        }
    }
}
