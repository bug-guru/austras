package guru.bug.austras.apt.events;

import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.events.model.DependencyCallParamModel;
import guru.bug.austras.apt.events.model.MessageCallParamModel;
import guru.bug.austras.apt.events.model.MessageReceiverModel;
import guru.bug.austras.apt.model.DependencyModel;
import guru.bug.austras.codegen.CompilationUnit;
import guru.bug.austras.codegen.common.CodeBlock;
import guru.bug.austras.codegen.common.QualifiedName;
import guru.bug.austras.codegen.decl.*;
import guru.bug.austras.codegen.spec.AnnotationSpec;
import guru.bug.austras.codegen.spec.TypeArg;
import guru.bug.austras.codegen.spec.TypeSpec;
import guru.bug.austras.core.Component;
import guru.bug.austras.engine.ProcessingContext;
import guru.bug.austras.events.Receiver;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

public class ReceiverGenerator {
    private final ModelUtils modelUtils;

    public ReceiverGenerator(ModelUtils modelUtils) {
        this.modelUtils = modelUtils;
    }

    public void generate(ProcessingContext ctx, ExecutableElement method) throws IOException {
        var model = createModel(ctx, method);

        var unit = CompilationUnit.builder()
                .packageDecl(PackageDecl.of(model.getPackageName()))
                .addTypeDecl(
                        TypeDecl.classBuilder()
                                .publicMod()
                                .addAnnotation(AnnotationSpec.of(Component.class))
                                .addAnnotations(modelUtils.createQualifierAnnotations(model.getQualifiers()))
                                .simpleName(model.getClassName())
                                .addSuperinterface(TypeSpec.builder()
                                        .name(QualifiedName.of(Receiver.class))
                                        .addTypeArg(TypeArg.ofType(model.getMessageType()))
                                        .build())
                                .addMembers(createFields(model))
                                .addMember(convertToConstructorDecl(model))
                                .addMember(createReceiverMethod(model))
                                .build())
                .build();

        ctx.fileManager().createFile(unit);

    }

    private Collection<ClassMemberDecl> createFields(MessageReceiverModel model) {
        return model.getDependencies().stream()
                .map(this::convertToField)
                .collect(Collectors.toList());
    }

    private FieldClassMemberDecl convertToField(DependencyModel dependencyModel) {
        return FieldClassMemberDecl.builder(modelUtils.convertToParameterType(dependencyModel), dependencyModel.getName())
                .privateMod()
                .finalMod()
                .build();
    }

    private ClassMemberDecl createReceiverMethod(MessageReceiverModel model) {
        return ClassMemberDecl.methodBuilder("receive", TypeSpec.voidType())
                .addAnnotation(AnnotationSpec.of(Override.class))
                .publicMod()
                .addParam(MethodParamDecl.builder()
                        .name(model.getMessageParam().getName())
                        .type(model.getMessageType())
                        .build())
                .body(createReceiverMethodBody(model))
                .build();
    }

    private CodeBlock createReceiverMethodBody(MessageReceiverModel model) {
        var builder = CodeBlock.builder();
        var params = model.getParameters().stream()
                .map(p -> {
                    if (p instanceof DependencyCallParamModel && ((DependencyCallParamModel) p).isResolveProvider()) {
                        return String.format("%s.get()", p.getName());
                    } else {
                        return p.getName();
                    }
                })
                .collect(Collectors.joining(", "));
        builder.addLine(String.format("this.%s.get().%s(%s);", model.getDependencies().get(0).getName(), model.getMethodName(), params));
        return builder.build();
    }

    private ClassMemberDecl convertToConstructorDecl(MessageReceiverModel model) {
        return ClassMemberDecl.constructorBuilder()
                .publicMod()
                .addParams(model.getDependencies().stream().map(modelUtils::convertToParametersDecl).collect(Collectors.toList()))
                .body(createConstructorBody(model))
                .build();
    }

    private CodeBlock createConstructorBody(MessageReceiverModel model) {
        var builder = CodeBlock.builder();
        for (var d : model.getDependencies()) {
            builder.addLine(String.format("this.%1$s = %1$s;", d.getName()));
        }
        return builder.build();
    }

    private MessageReceiverModel createModel(ProcessingContext ctx, ExecutableElement method) {
        var result = new MessageReceiverModel();
        var processingEnv = ctx.processingEnv();
        var packageName = processingEnv.getElementUtils().getPackageOf(method).getQualifiedName().toString();
        result.setPackageName(packageName);
        var receiverClassName = method.getEnclosingElement().getSimpleName() + StringUtils.capitalize(method.getSimpleName().toString()) + "Receiver";
        result.setClassName(receiverClassName);
        VariableElement messageParamElement = null;

        var methodName = method.getSimpleName();
        result.setMethodName(methodName.toString());

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
                result.setMessageParam(d);
            } else {
                var origDep = modelUtils.createDependencyModel(p);
                var dependency = origDep.copyAsProvider();
                var callParam = new DependencyCallParamModel();
                callParam.setResolveProvider(!origDep.isProvider());
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
            var mp = new MessageCallParamModel();
            mp.setName("aVoid");
            mp.setType(Void.class.getName());
            result.setMessageParam(mp);
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


}
