package guru.bug.austras.convert.apt;

import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.engine.json.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ConvertersProcessor {
    private final Logger logger = LoggerFactory.getLogger(ConvertersProcessor.class);
    private final ProcessingContext ctx;
    private final TypeElement contentConverter;
    private final TypeElement jsonConverter;

    ConvertersProcessor(ProcessingContext ctx) {
        this.ctx = ctx;
        var elementUtils = ctx.processingEnv().getElementUtils();
        contentConverter = elementUtils.getTypeElement(ContentConverter.class.getName());
        jsonConverter = elementUtils.getTypeElement(JsonConverter.class.getName());
    }

    Set<DeclaredType> process() {
        return ctx.roundEnv().getRootElements()
                .stream()
                .map(this::asComponent)
                .filter(Objects::nonNull)
                .flatMap(this::constructorsOf)
                .flatMap(this::parametersOf)
                .map(this::generateIfNeeded)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableSet());
    }


    private DeclaredType generateIfNeeded(VariableElement parameter) {
        var paramType = parameter.asType();
        if (paramType.getKind() != TypeKind.DECLARED) {
            return null;
        }
        var paramDeclType = (DeclaredType) paramType;
        var args = paramDeclType.getTypeArguments();
        if (args.size() != 1) {
            return null;
        }
        var paramTypeElement = paramDeclType.asElement();
        if (!contentConverter.equals(paramTypeElement) && !jsonConverter.equals(paramTypeElement)) {
            return null;
        }
        var qualifier = ctx.componentManager().extractQualifier(parameter);
        if (ctx.componentManager().tryUseComponents(paramType, qualifier)) {
            return null;
        }

        var convType = args.get(0);
        if (convType.getKind() != TypeKind.DECLARED) {
            return null;
        }
        return (DeclaredType) args.get(0);
    }


    private Stream<? extends VariableElement> parametersOf(ExecutableElement executableElement) {
        return executableElement.getParameters().stream();
    }

    private Stream<ExecutableElement> constructorsOf(TypeElement typeElement) {
        return typeElement.getEnclosedElements().stream()
                .map(this::asConstructor)
                .filter(Objects::nonNull);
    }

    private ExecutableElement asConstructor(Element element) {
        if (element.getKind() == ElementKind.CONSTRUCTOR) {
            return (ExecutableElement) element;
        } else {
            return null;
        }
    }

    private TypeElement asComponent(Element element) {
        if (element.getKind() != ElementKind.CLASS) {
            return null;
        }
        var typeElement = (TypeElement) element;
        var modifiers = typeElement.getModifiers();
        if (!modifiers.contains(Modifier.PUBLIC)) {
            return null;
        }
        if (modifiers.contains(Modifier.ABSTRACT)) {
            return null;
        }
        return typeElement;
    }
}
