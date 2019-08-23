package guru.bug.austras.apt.events;

import com.squareup.javapoet.*;
import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.events.model.DependencyCallParamModel;
import guru.bug.austras.apt.events.model.MessageCallParamModel;
import guru.bug.austras.apt.events.model.MessageReceiverModel;
import guru.bug.austras.apt.model.DependencyModel;
import guru.bug.austras.apt.model.QualifierModel;
import guru.bug.austras.core.Component;
import guru.bug.austras.core.Qualifier;
import guru.bug.austras.core.QualifierProperty;
import guru.bug.austras.events.Broadcaster;
import guru.bug.austras.events.Message;
import guru.bug.austras.events.Receiver;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReceiverGenerator {
    private final ProcessingEnvironment processingEnv;
    private final Elements elementUtils;
    private final ModelUtils modelUtils;
    private final Types typeUtils;
    private final Filer filer;
    private final TypeElement receiverElement;
    private final TypeElement broadcaster;
    private final TypeMirror voidType;

    public ReceiverGenerator(ProcessingEnvironment processingEnv, ModelUtils modelUtils) {
        this.processingEnv = processingEnv;
        this.filer = processingEnv.getFiler();
        this.typeUtils = processingEnv.getTypeUtils();
        this.elementUtils = processingEnv.getElementUtils();
        this.modelUtils = modelUtils;
        this.receiverElement = elementUtils.getTypeElement(Receiver.class.getName());
        this.broadcaster = elementUtils.getTypeElement(Broadcaster.class.getName());
        this.voidType = elementUtils.getTypeElement(Void.class.getName()).asType();
    }

    public void generate(ExecutableElement method) throws IOException {
        var model = createModel(method);

        var receiverParamType = extractReceiverParamType(method);
        var superInterface = typeUtils.getDeclaredType(receiverElement, receiverParamType);

        JavaFile.builder(model.getPackageName(),
                TypeSpec.classBuilder(model.getClassName())
                        .addAnnotation(Component.class)
                        .addAnnotations(createQualifierAnnotations(method))
                        .addSuperinterface(TypeName.get(superInterface))
                        .add
                        .build()
        ).build()
                .writeTo(filer);
    }

    private MessageReceiverModel createModel(ExecutableElement method) {
        var result = new MessageReceiverModel();
        var packageName = elementUtils.getPackageOf(method).getQualifiedName().toString();
        result.setPackageName(packageName);
        var receiverClassName = method.getEnclosingElement().getSimpleName() + StringUtils.capitalize(method.getSimpleName().toString()) + "Receiver";
        result.setClassName(receiverClassName);
        VariableElement messageParamElement = null;

        var componentDependency = createComponentDependency(method);
        result.addDependency(componentDependency);

        for (var p : method.getParameters()) {
            var paramQualifiers = modelUtils.extractQualifiers(p);
            if (messageParamElement == null
                    && !modelUtils.isBroadcaster(p.asType())
                    && paramQualifiers.contains(Receiver.MESSAGE_QUALIFIER_NAME)) {
                result.setQualifiers(paramQualifiers);
                messageParamElement = p;
                var d = new MessageCallParamModel();
                d.setName(p.getSimpleName().toString());
                d.setType(p.asType().toString());
                result.addParameter(d);
            } else {
                var dependency = modelUtils.createDependencyModel(p);
                var callParam = new DependencyCallParamModel();
                callParam.setDependency(dependency);
                callParam.setName(dependency.getName());
                callParam.setType(dep);
            }
        }

        return new MessageReceiverModel();
    }

    private DependencyModel createComponentDependency(ExecutableElement method) {
        var componentElement = (TypeElement) method.getEnclosingElement();
        DeclaredType componentType = (DeclaredType) componentElement.asType();
        // TODO varName must be unique
        var componentDependency = modelUtils.createDependencyModel("receiverComponentProvider", componentType, componentElement);
        componentDependency.setProvider(true);
        return componentDependency;
    }


    protected final String generateQualifierAnnotations(QualifierModel qualifier, boolean multiline) {
        if (qualifier == null || qualifier.isEmpty()) {
            return "";
        }
        var result = new StringBuilder(256);
        qualifier.forEach((qualifierName, props) -> {
            var strProps = props.stream()
                    .map(e -> String.format("@%s(name=\"%s\",value=\"%s\")",
                            QualifierProperty.class.getName(),
                            StringEscapeUtils.escapeJava(e.getLeft()),
                            StringEscapeUtils.escapeJava(e.getRight())))
                    .collect(Collectors.joining(",", "{", "}"));
            var qline = String.format("@%s(name=\"%s\", properties=%s)", Qualifier.class.getName(), qualifierName, props);
            result.append(qline);
            if (multiline) {
                result.append('\n');
            } else {
                result.append(' ');
            }
        });
        return result.toString();
    }

    private List<AnnotationSpec> createQualifierAnnotations(Element element) {
        var result = new ArrayList<AnnotationSpec>();
        modelUtils.extractQualifiers(element).forEach((qualifierName, properties) -> {
            AnnotationSpec.Builder qualifierBuilder = AnnotationSpec.builder(Qualifier.class)
                    .addMember("name", "$S", qualifierName);
            var propsBuilder = CodeBlock.builder().beginControlFlow("")
            properties.forEach(prop -> {
                CodeBlock.builder().
                                            .forEach((k, v) -> result.addMember(k, "%S", v));

            });
        });
                .map(m -> {
                    m.getProperties()
                    return result.build();
                })
                .collect(Collectors.toList());
    }

    private TypeMirror extractReceiverParamType(ExecutableElement method) {
        return method.getParameters().stream()
                .filter(p -> p.getAnnotationsByType(Message.class).length > 0)
                .filter(p -> !typeUtils.isAssignable(p.asType(), broadcaster.asType()))
                .findFirst()
                .map(Element::asType)
                .orElse(voidType);
    }

    private List<QualifierModel> extractReceiverQualifiers(ExecutableElement method) {
        return method.getParameters().stream()
                .filter(p -> p.getAnnotationsByType(Message.class).length > 0)
                .filter(p -> !typeUtils.isAssignable(p.asType(), broadcaster.asType()))
                .findFirst()
                .map(modelUtils::extractQualifiers)
                .orElseGet(() -> modelUtils.extractQualifiers(method));
    }

}
