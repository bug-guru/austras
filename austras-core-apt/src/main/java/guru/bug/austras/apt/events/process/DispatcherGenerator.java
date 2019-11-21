package guru.bug.austras.apt.events.process;

import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.apt.core.model.DependencyModel;
import guru.bug.austras.apt.events.model.CallParamModel;
import guru.bug.austras.apt.events.model.MessageCallParamModel;
import guru.bug.austras.apt.events.model.MessageDispatcherModel;
import guru.bug.austras.codegen.BodyBlock;
import guru.bug.austras.codegen.FromTemplate;
import guru.bug.austras.codegen.JavaGenerator;
import guru.bug.austras.codegen.TemplateException;
import guru.bug.austras.provider.Provider;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.stream.Collectors;

@FromTemplate("Dispatcher.java.txt")
public class DispatcherGenerator extends JavaGenerator {
    private static final String MESSAGE_QUALIFIER_NAME = "austras.message";
    private final ProcessingContext ctx;
    private MessageDispatcherModel dispatcherModel;
    private DependencyModel currentDependency;
    private String optionalComma;

    DispatcherGenerator(ProcessingContext ctx) throws IOException, TemplateException {
        super(ctx.processingEnv().getFiler());
        this.ctx = ctx;
    }

    void generate(ExecutableElement method) {
        createModel(method);
        super.generateJavaClass();
    }

    private void createModel(ExecutableElement method) {
        dispatcherModel = new MessageDispatcherModel();
        var processingEnv = ctx.processingEnv();
        var packageName = processingEnv.getElementUtils().getPackageOf(method).getQualifiedName().toString();
        dispatcherModel.setPackageName(packageName);
        var receiverClassName = method.getEnclosingElement().getSimpleName() + StringUtils.capitalize(method.getSimpleName().toString()) + "Dispatcher";
        dispatcherModel.setClassName(receiverClassName);
        VariableElement messageParamElement = null;

        var methodName = method.getSimpleName();
        dispatcherModel.setMethodName(methodName.toString());

        var componentDependency = createComponentDependency(method);
        dispatcherModel.addDependency(componentDependency);

        for (var p : method.getParameters()) {
            var paramQualifiers = ctx.modelUtils().extractQualifiers(p);
            if (messageParamElement == null
                    && !ctx.modelUtils().isBroadcaster(p.asType())
                    && paramQualifiers.contains(MESSAGE_QUALIFIER_NAME)) {
                dispatcherModel.setQualifiers(paramQualifiers);
                messageParamElement = p;
                var d = new MessageCallParamModel();
                d.setName(p.getSimpleName().toString());
                d.setType(p.asType().toString());
                dispatcherModel.addParameter(d);
                dispatcherModel.setMessageParam(d);
            } else {
                var origDep = ctx.modelUtils().createDependencyModel(p);
                var dependency = origDep.copyAsProvider();
                var callParam = new CallParamModel();
                callParam.setResolveProvider(!origDep.isProvider());
                callParam.setDependency(dependency);
                callParam.setName(dependency.getName());
                callParam.setType(dependency.getType());
                dispatcherModel.addDependency(dependency);
                dispatcherModel.addParameter(callParam);
            }
        }
        if (messageParamElement == null) {
            dispatcherModel.setMessageType(Void.class.getName());
            dispatcherModel.setQualifiers(ctx.modelUtils().extractQualifiers(method));
            var mp = new MessageCallParamModel();
            mp.setName("_aVoid");
            mp.setType(Void.class.getName());
            dispatcherModel.setMessageParam(mp);
        } else {
            dispatcherModel.setMessageType(messageParamElement.asType().toString());
        }


    }

    private DependencyModel createComponentDependency(ExecutableElement method) {
        var componentElement = (TypeElement) method.getEnclosingElement();
        DeclaredType componentType = (DeclaredType) componentElement.asType();
        // TODO varName must be unique
        var componentDependency = ctx.modelUtils().createDependencyModel("_receiverComponentsProvider", componentType, componentElement);
        componentDependency.setProvider(true);
        componentDependency.setCollection(true);
        return componentDependency;
    }

    @FromTemplate("PACKAGE_NAME")
    @Override
    public String getPackageName() {
        return dispatcherModel.getPackageName();
    }

    @FromTemplate("SIMPLE_CLASS_NAME")
    @Override
    public String getSimpleClassName() {
        return dispatcherModel.getClassName();
    }

    @FromTemplate("QUALIFIERS")
    public String qualifiers() {
        return ModelUtils.qualifierToString(dispatcherModel.getQualifier());
    }


    @FromTemplate("MESSAGE_TYPE")
    public String messageType() {
        return tryImport(dispatcherModel.getMessageType());
    }

    @FromTemplate("DEPENDENCIES")
    public void dependencies(PrintWriter out, BodyBlock body) {
        var i = dispatcherModel.getDependencies().iterator();
        while (i.hasNext()) {
            this.currentDependency = i.next();
            if (i.hasNext()) {
                this.optionalComma = ", ";
            } else {
                this.optionalComma = "";
            }
            out.print(body.evaluateBody());
        }
    }

    @FromTemplate("OPTIONAL_COMMA")
    public String optionalComma() {
        return optionalComma;
    }

    @FromTemplate("DEPENDENCY_PROVIDER_CLASS")
    public String dependencyProviderClass() {
        var result = tryImport(currentDependency.getType());
        if (currentDependency.isCollection()) {
            result = tryImport(Collection.class.getName()) + "<? extends " + result + ">";
        }
        if (currentDependency.isProvider()) {
            result = tryImport(Provider.class.getName()) + "<? extends " + result + ">";
        }
        return result;
    }

    @FromTemplate("DEPENDENCY_QUALIFIERS")
    public String dependencyQualifiers() {
        return ModelUtils.qualifierToString(currentDependency.getQualifiers());
    }

    @FromTemplate("TARGET_PROVIDER_NAME")
    public String targetProviderName() {
        return currentDependency.getName();
    }

    @FromTemplate("RECEIVERS_DEPENDENCY_PROVIDER_NAME")
    public String receiversDependencyProviderName() {
        return dispatcherModel.getDependencies().get(0).getName();
    }

    @FromTemplate("RECEIVER_METHOD_NAME")
    public String receiverMethodName() {
        return dispatcherModel.getMethodName();
    }

    @FromTemplate("MESSAGE_PARAM_NAME")
    public String messageParamName() {
        return dispatcherModel.getMessageParam().getName();
    }

    @FromTemplate("RECEIVER_METHOD_PARAMS")
    public String receiverMethodParams() {
        return dispatcherModel.getParameters().stream()
                .map(p -> {
                    if (p.isResolveProvider()) {
                        return String.format("%s.get()", p.getName());
                    } else {
                        return p.getName();
                    }
                })
                .collect(Collectors.joining(", "));
    }
}