package guru.bug.austras.apt.events;

import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.events.model.DependencyCallParamModel;
import guru.bug.austras.apt.events.model.MessageCallParamModel;
import guru.bug.austras.apt.events.model.MessageReceiverModel;
import guru.bug.austras.apt.model.DependencyModel;
import guru.bug.austras.apt.model.QualifierModel;
import guru.bug.austras.code.CompilationUnit;
import guru.bug.austras.code.common.CodeBlock;
import guru.bug.austras.code.common.QualifiedName;
import guru.bug.austras.code.decl.ClassMemberDecl;
import guru.bug.austras.code.decl.MethodParamDecl;
import guru.bug.austras.code.decl.PackageDecl;
import guru.bug.austras.code.decl.TypeDecl;
import guru.bug.austras.code.spec.AnnotationSpec;
import guru.bug.austras.code.spec.TypeArg;
import guru.bug.austras.code.spec.TypeSpec;
import guru.bug.austras.core.Component;
import guru.bug.austras.core.Qualifier;
import guru.bug.austras.core.QualifierProperty;
import guru.bug.austras.events.Receiver;
import guru.bug.austras.provider.Provider;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ReceiverGenerator {
    private final Elements elementUtils;
    private final ModelUtils modelUtils;
    private final Filer filer;

    public ReceiverGenerator(ProcessingEnvironment processingEnv, ModelUtils modelUtils) {
        this.filer = processingEnv.getFiler();
        this.elementUtils = processingEnv.getElementUtils();
        this.modelUtils = modelUtils;
    }

    public void generate(ExecutableElement method) throws IOException {
        var model = createModel(method);

        var unit = CompilationUnit.builder()
                .packageDecl(PackageDecl.of(model.getPackageName()))
                .addTypeDecl(
                        TypeDecl.classBuilder()
                                .publicMod()
                                .addAnnotation(AnnotationSpec.of(Component.class))
                                .addAnnotations(createQualifierAnnotations(model.getQualifiers()))
                                .simpleName(model.getClassName())
                                .addSuperinterface(TypeSpec.builder()
                                        .name(QualifiedName.of(Receiver.class))
                                        .addTypeArg(TypeArg.ofType(model.getMessageType()))
                                        .build())
                                .addMember(convertToConstructorDecl(model))
                                .addMember(createReceiverMethod(model))
                                .build())
                .build();

        try (var writer = filer.createSourceFile(unit.getQualifiedName()).openWriter();
             var out = new PrintWriter(writer)) {
            unit.print(out);
        }

    }

    private ClassMemberDecl createReceiverMethod(MessageReceiverModel model) {
        return ClassMemberDecl.methodBuilder("receive", TypeSpec.voidType())
                .addAnnotation(AnnotationSpec.of(Override.class))
                .publicMod()
                .addParam(MethodParamDecl.builder()
                        .name("message")
                        .type(model.getMessageType())
                        .build())
                .body(new CodeBlock())
                .build();
    }

    private ClassMemberDecl convertToConstructorDecl(MessageReceiverModel model) {
        return ClassMemberDecl.constructorBuilder()
                .publicMod()
                .addParams(model.getDependencies().stream().map(this::convertToParametersDecl).collect(Collectors.toList()))
                .body(new CodeBlock())
                .build();
    }

    private MethodParamDecl convertToParametersDecl(DependencyModel model) {
        return MethodParamDecl.builder()
                .addAnnotations(createQualifierAnnotations(model.getQualifiers()))
                .name(model.getName())
                .type(calculateParameterType(model))
                .build();
    }

    private TypeSpec calculateParameterType(DependencyModel model) {
        var result = TypeSpec.of(model.getType());
        if (model.isCollection()) {
            result = TypeSpec.builder()
                    .name(QualifiedName.of(Collection.class))
                    .addTypeArg(TypeArg.wildcardExtends(result))
                    .build();
        }
        if (model.isProvider()) {
            result = TypeSpec.builder()
                    .name(QualifiedName.of(Provider.class))
                    .addTypeArg(TypeArg.wildcardExtends(result))
                    .build();
        }
        return result;
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
                var dependency = modelUtils.createDependencyModel(p).copyAsProvider();
                var callParam = new DependencyCallParamModel();
                callParam.setDependency(dependency);
                callParam.setName(dependency.getName());
                callParam.setType(dependency.getType());
                result.addDependency(dependency);
                result.addParameter(callParam);
            }
        }
        if (messageParamElement == null) {
            result.setMessageType(Void.class.getName());
            result.setQualifiers(modelUtils.extractQualifiers(method));
        } else {
            result.setMessageType(messageParamElement.asType().toString());
        }

        return result;
    }

    private DependencyModel createComponentDependency(ExecutableElement method) {
        var componentElement = (TypeElement) method.getEnclosingElement();
        DeclaredType componentType = (DeclaredType) componentElement.asType();
        // TODO varName must be unique
        var componentDependency = modelUtils.createDependencyModel("receiverComponentProvider", componentType, componentElement);
        componentDependency.setProvider(true);
        return componentDependency;
    }

    private List<AnnotationSpec> createQualifierAnnotations(QualifierModel qualifierModel) {
        var result = new ArrayList<AnnotationSpec>();
        qualifierModel.forEach((qualifierName, properties) -> {
            var qualifierBuilder = AnnotationSpec.builder().typeName(Qualifier.class)
                    .add("name", qualifierName);
            properties.forEach(prop ->
                    qualifierBuilder.add("properties",
                            AnnotationSpec.builder()
                                    .typeName(QualifierProperty.class)
                                    .add("name", prop.getKey())
                                    .add("value", prop.getValue())
                                    .build()));
            result.add(qualifierBuilder.build());
        });
        return result;
    }

}
