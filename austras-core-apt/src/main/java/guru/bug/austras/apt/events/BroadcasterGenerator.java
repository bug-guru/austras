package guru.bug.austras.apt.events;

import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.events.model.MessageBroadcasterModel;
import guru.bug.austras.codegen.CompilationUnit;
import guru.bug.austras.codegen.common.CodeBlock;
import guru.bug.austras.codegen.common.QualifiedName;
import guru.bug.austras.codegen.decl.*;
import guru.bug.austras.codegen.spec.AnnotationSpec;
import guru.bug.austras.codegen.spec.TypeArg;
import guru.bug.austras.codegen.spec.TypeSpec;
import guru.bug.austras.core.Component;
import guru.bug.austras.engine.ProcessingContext;
import guru.bug.austras.events.Broadcaster;
import guru.bug.austras.events.Receiver;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import java.io.IOException;
import java.util.Collection;

public class BroadcasterGenerator {

    private final ModelUtils modelUtils;

    public BroadcasterGenerator(ModelUtils modelUtils) {
        this.modelUtils = modelUtils;
    }

    public void generate(ProcessingContext ctx, VariableElement e) throws IOException {
        var model = createModel(ctx, e);
        var qualifiers = modelUtils.createQualifierAnnotations(model.getQualifier());
        var unit = CompilationUnit.builder()
                .packageDecl(PackageDecl.of(model.getPackageName()))
                .addTypeDecl(TypeDecl.classBuilder()
                        .publicMod()
                        .addAnnotation(AnnotationSpec.of(Component.class))
                        .addAnnotations(qualifiers)
                        .simpleName(model.getSimpleName())
                        .superclass(TypeSpec.builder()
                                .name(QualifiedName.of(Broadcaster.class))
                                .addTypeArg(TypeArg.ofType(model.getType()))
                                .build())
                        .addMember(createConstructor(model))
                        .build())
                .build();
        ctx.fileManager().createFile(unit);
    }

    private MethodClassMemberDecl createConstructor(MessageBroadcasterModel model) {
        var qualifiers = modelUtils.createQualifierAnnotations(model.getQualifier());
        return ClassMemberDecl.constructorBuilder()
                .publicMod()
                .addParam(MethodParamDecl.builder()
                        .name("receivers")
                        .addAnnotations(qualifiers)
                        .type(TypeSpec.builder()
                                .name(QualifiedName.of(Collection.class))
                                .addTypeArg(TypeArg.wildcardExtends(TypeSpec.builder()
                                        .name(QualifiedName.of(Receiver.class))
                                        .addTypeArg(TypeArg.ofType(model.getType()))
                                        .build()))
                                .build())
                        .build())
                .body(createConstructorBody())
                .build();
    }

    private CodeBlock createConstructorBody() {
        return CodeBlock.builder()
                .addLine("super(receivers);")
                .build();
    }

    private MessageBroadcasterModel createModel(ProcessingContext ctx, VariableElement e) {
        var result = new MessageBroadcasterModel();

        var packageName = ctx.processingEnv().getElementUtils().getPackageOf(e).getQualifiedName().toString();
        result.setPackageName(packageName);
        var paramType = (DeclaredType) e.asType();
        var msgType = modelUtils.extractComponentTypeFromBroadcaster(paramType);

        result.setType(msgType.toString());

        var qualifier = modelUtils.extractQualifiers(e);
        result.setQualifier(qualifier);

        var method = e.getEnclosingElement();
        var clazz = method.getEnclosingElement();
        var paramName = e.getSimpleName().toString();
        var simpleName = clazz.getSimpleName() + StringUtils.capitalize(paramName) + "Broadcaster";
        result.setSimpleName(simpleName);

        return result;
    }
}
