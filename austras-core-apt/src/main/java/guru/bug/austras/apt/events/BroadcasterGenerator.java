package guru.bug.austras.apt.events;

import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.events.model.MessageBroadcasterModel;
import guru.bug.austras.codegen.FromTemplate;
import guru.bug.austras.codegen.JavaGenerator;
import guru.bug.austras.core.Qualifier;
import guru.bug.austras.engine.ProcessingContext;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import java.io.IOException;
import java.util.stream.Collectors;

@FromTemplate("Broadcaster.java.txt")
public class BroadcasterGenerator extends JavaGenerator {

    private final ModelUtils modelUtils;
    private MessageBroadcasterModel messageBroadcasterModel;

    public BroadcasterGenerator(ProcessingContext ctx, ModelUtils modelUtils) throws IOException {
        super(ctx.processingEnv().getFiler());
        this.modelUtils = modelUtils;
    }

    public void generate(ProcessingContext ctx, VariableElement e) throws IOException {
        messageBroadcasterModel = createModel(ctx, e);
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
        return messageBroadcasterModel.getType();
    }

    @FromTemplate("QUALIFIERS")
    public String qualifiers() {
        var result = new StringBuilder(512);
        result.setLength(0);
        messageBroadcasterModel.getQualifier().forEach((qualifierName, properties) -> {
            result.append("@")
                    .append(Qualifier.class.getSimpleName())
                    .append("(name = \"")
                    .append(qualifierName)
                    .append("\"");
            if (!properties.isEmpty()) {
                result.append(", properties = ");
                if (properties.size() > 1) {
                    result.append("{");
                }
                result.append(properties.stream()
                        .map(p -> String.format("@QualifierProperty(name = \"%s\", value = \"%s\"", p.getKey(), p.getValue()))
                        .collect(Collectors.joining(", "))
                );
                if (properties.size() > 1) {
                    result.append("}");
                }
            }
            result.append(") ");
        });
        return result.toString();
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
