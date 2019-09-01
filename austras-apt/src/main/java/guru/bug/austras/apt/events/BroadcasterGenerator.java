package guru.bug.austras.apt.events;

import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.events.model.MessageBroadcasterModel;
import guru.bug.austras.code.CompilationUnit;
import guru.bug.austras.code.common.QualifiedName;
import guru.bug.austras.code.decl.PackageDecl;
import guru.bug.austras.code.decl.TypeDecl;
import guru.bug.austras.code.spec.TypeArg;
import guru.bug.austras.code.spec.TypeSpec;
import guru.bug.austras.events.Broadcaster;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.io.PrintWriter;

public class BroadcasterGenerator {
    private final Elements elementUtils;
    private final Filer filer;
    private final ProcessingEnvironment processingEnv;
    private final ModelUtils modelUtils;

    public BroadcasterGenerator(ProcessingEnvironment processingEnv, ModelUtils modelUtils) {
        this.filer = processingEnv.getFiler();
        this.elementUtils = processingEnv.getElementUtils();
        this.processingEnv = processingEnv;
        this.modelUtils = modelUtils;
    }

    public void generate(VariableElement e) throws IOException {
        var model = createModel(e);
        var unit = CompilationUnit.builder()
                .packageDecl(PackageDecl.of(model.getPackageName()))
                .addTypeDecl(TypeDecl.classBuilder()
                        .publicMod()
                        .simpleName(model.getSimpleName())
                        .addSuperinterface(TypeSpec.builder()
                                .name(QualifiedName.of(Broadcaster.class))
                                .addTypeArg(TypeArg.ofType(model.getType()))
                                .build())
                        .build())
                .build();
        try (var writer = filer.createSourceFile(unit.getQualifiedName()).openWriter();
             var out = new PrintWriter(writer)) {
            unit.print(out);
        }
    }

    private MessageBroadcasterModel createModel(VariableElement e) {
        var result = new MessageBroadcasterModel();

        var packageName = elementUtils.getPackageOf(e).getQualifiedName().toString();
        result.setPackageName(packageName);
        var paramType = (DeclaredType) e.asType();
        var msgType = modelUtils.extractComponentTypeFromBroadcaster(paramType);

        result.setType(msgType.toString());

        var qualifier = modelUtils.extractQualifiers(e);
        result.setQualifier(qualifier);

        var method = e.getEnclosingElement();
        var clazz = method.getEnclosingElement();
        var simpleName = clazz.getSimpleName() + StringUtils.capitalize(e.getSimpleName().toString()) + "Broadcaster";
        result.setSimpleName(simpleName);

        return result;
    }
}
