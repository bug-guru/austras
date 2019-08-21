package guru.bug.austras.apt.events;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.model.QualifierModel;
import guru.bug.austras.core.Component;
import guru.bug.austras.core.Qualifier;
import guru.bug.austras.events.Broadcaster;
import guru.bug.austras.events.Message;
import guru.bug.austras.events.Receiver;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.IOException;
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
        var packageName = elementUtils.getPackageOf(method).getQualifiedName().toString();
        var receiverName = method.getEnclosingElement().getSimpleName() + StringUtils.capitalize(method.getSimpleName().toString()) + "Receiver";
        var receiverParamType = extractReceiverParamType(method);
        var superInterface = typeUtils.getDeclaredType(receiverElement, receiverParamType);
        var qualifiers = extractReceiverQualifiers(method).stream()
                .map(m -> {
                    AnnotationSpec.Builder result = AnnotationSpec.builder(Qualifier.class)
                            .addMember("name", "$S", m.getName());
                    m.getProperties()
                            .forEach((k, v) -> result.addMember(k, "%S", v));
                    return result.build();
                })
                .collect(Collectors.toList());


        JavaFile.builder(packageName,
                TypeSpec.classBuilder(receiverName)
                        .addAnnotation(Component.class)
                        .addAnnotations(qualifiers)
                        .addSuperinterface(TypeName.get(superInterface))
                        .build()
        ).build()
                .writeTo(filer);
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
