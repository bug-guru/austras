package guru.bug.austras.convert.apt;

import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.codegen.TemplateException;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.engine.json.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

class ConvertersProcessor {
    private final Logger logger = LoggerFactory.getLogger(ConvertersProcessor.class);
    private final ProcessingContext ctx;
    private final TypeElement jsonConverter;
    private final TypeElement stringConverter;
    private final JsonConverterGenerator jsonConverterGenerator;

    ConvertersProcessor(ProcessingContext ctx) {
        this.ctx = ctx;
        var elementUtils = ctx.processingEnv().getElementUtils();
        jsonConverter = elementUtils.getTypeElement(JsonConverter.class.getName());
        stringConverter = elementUtils.getTypeElement(StringConverter.class.getName());
        try {
            jsonConverterGenerator = new JsonConverterGenerator(ctx);
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }
    }

    void process() {
        ctx.roundEnv().getRootElements()
                .stream()
                .map(this::asComponent)
                .filter(Objects::nonNull)
                .flatMap(this::constructorsOf)
                .flatMap(this::parametersOf)
                .forEach(this::generateIfNeeded);
    }


    private void generateIfNeeded(VariableElement parameter) {
        var paramType = parameter.asType();
        if (paramType.getKind() != TypeKind.DECLARED) {
            return;
        }
        var paramDeclType = (DeclaredType) paramType;
        var args = paramDeclType.getTypeArguments();
        if (args.size() != 1) {
            return;
        }
        var paramTypeElement = paramDeclType.asElement();
        boolean isJsonConverter = jsonConverter.equals(paramTypeElement);
        boolean isStringConverter = stringConverter.equals(paramTypeElement);
        if (!isJsonConverter && !isStringConverter) {
            return;
        }
        var qualifier = ctx.componentManager().extractQualifier(parameter);
        if (ctx.componentManager().tryUseComponents(paramType, qualifier)) {
            return;
        }

        var convType = args.get(0);
        if (convType.getKind() != TypeKind.DECLARED) {
            return;
        }
        var convDeclType = (DeclaredType) args.get(0);

        if (isJsonConverter) {
            generateJsonConverter(convDeclType);
        } else {
            generateStringConverter(convDeclType);
        }
    }

    private void generateStringConverter(DeclaredType conversionType) { //NOSONAR not yet finished
        logger.info("GENERATING STRING CONVERTER FOR {}" , conversionType);
        // TODO
    }

    private void generateJsonConverter(DeclaredType conversionType) {
        logger.info("generating json converter for {}" , conversionType);
        jsonConverterGenerator.generate(conversionType);
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
