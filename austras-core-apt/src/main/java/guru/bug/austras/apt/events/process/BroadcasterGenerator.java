package guru.bug.austras.apt.events.process;

import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.apt.events.model.MessageBroadcasterModel;
import guru.bug.austras.codegen.FromTemplate;
import guru.bug.austras.codegen.JavaGenerator;
import guru.bug.austras.codegen.TemplateException;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import java.io.IOException;

@FromTemplate("Broadcaster.java.txt")
public class BroadcasterGenerator extends JavaGenerator {

    private final ProcessingContext ctx;
    private MessageBroadcasterModel messageBroadcasterModel;

    BroadcasterGenerator(ProcessingContext ctx) throws IOException, TemplateException {
        super(ctx.processingEnv().getFiler());
        this.ctx = ctx;
    }

    void generate(VariableElement e) {
        messageBroadcasterModel = createModel(e);
        super.generateJavaClass();
    }

    @Override
    @FromTemplate("PACKAGE_NAME")
    public String getPackageName() {
        return messageBroadcasterModel.getPackageName();
    }

    @Override
    @FromTemplate("SIMPLE_CLASS_NAME")
    public String getSimpleClassName() {
        return messageBroadcasterModel.getSimpleName();
    }

    @FromTemplate("MESSAGE_TYPE")
    public String getMessageType() {
        return tryImport(messageBroadcasterModel.getType());
    }

    @FromTemplate("QUALIFIERS")
    public String qualifiers() {
        return ModelUtils.qualifierToString(messageBroadcasterModel.getQualifier());
    }

    private MessageBroadcasterModel createModel(VariableElement e) {
        var result = new MessageBroadcasterModel();

        var packageName = ctx.processingEnv().getElementUtils().getPackageOf(e).getQualifiedName().toString();
        result.setPackageName(packageName);
        var paramType = (DeclaredType) e.asType();
        var msgType = ctx.modelUtils().extractComponentTypeFromBroadcaster(paramType);

        result.setType(msgType.toString());

        var qualifier = ctx.modelUtils().extractQualifiers(e);
        result.setQualifier(qualifier);

        var method = e.getEnclosingElement();
        var clazz = method.getEnclosingElement();
        var paramName = e.getSimpleName().toString();
        var simpleName = clazz.getSimpleName() + StringUtils.capitalize(paramName) + "Broadcaster";
        result.setSimpleName(simpleName);

        return result;
    }
}