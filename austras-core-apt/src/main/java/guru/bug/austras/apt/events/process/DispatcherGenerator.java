package guru.bug.austras.apt.events.process;

import guru.bug.austras.apt.core.common.model.DependencyModel;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.apt.events.model.MessageDispatcherModel;
import guru.bug.austras.codegen.FromTemplate;
import guru.bug.austras.codegen.JavaGenerator;
import guru.bug.austras.codegen.TemplateException;
import guru.bug.austras.core.Selector;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.io.IOException;

@FromTemplate("Dispatcher.java.txt")
public class DispatcherGenerator extends JavaGenerator {
    private static final String MESSAGE_QUALIFIER_NAME = "austras.message";
    private final ProcessingContext ctx;
    private MessageDispatcherModel dispatcherModel;

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

        var methodName = method.getSimpleName();
        dispatcherModel.setMethodName(methodName.toString());

        var componentDependency = createComponentDependency(method);
        dispatcherModel.setComponentDependency(componentDependency);

        var qualifiers = ctx.modelUtils().extractQualifiers(method);
        dispatcherModel.setQualifiers(qualifiers);

        var methodParams = method.getParameters();
        if (methodParams.isEmpty()) {
            dispatcherModel.setMessageType(Void.class.getName());
        } else if (methodParams.size() == 1) {
            var param = methodParams.get(0);
            dispatcherModel.setMessageType(param.asType().toString());
        } else {
            throw new IllegalStateException();
        }
    }

    private DependencyModel createComponentDependency(ExecutableElement method) {
        var componentElement = (TypeElement) method.getEnclosingElement();
        DeclaredType componentType = (DeclaredType) componentElement.asType();
        var result = ctx.modelUtils().createDependencyModel(componentType, componentElement);
        return result.copyAsSelector();
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
        return qualifierToString(dispatcherModel.getQualifier());
    }


    @FromTemplate("MESSAGE_TYPE")
    public String messageType() {
        return tryImport(dispatcherModel.getMessageType());
    }

    @FromTemplate("RECEIVERS_SELECTOR_TYPE")
    public String dependencyProviderClass() {
        var componentDependency = dispatcherModel.getComponentDependency();
        var selector = String.format("%s<? extends %s>",
                Selector.class.getName(),
                componentDependency.getType());
        return tryImport(selector);
    }

    @FromTemplate("RECEIVERS_SELECTOR_QUALIFIERS")
    public String dependencyQualifiers() {
        var componentDependency = dispatcherModel.getComponentDependency();
        return qualifierToString(componentDependency.getQualifiers());
    }

    @FromTemplate("RECEIVERS_SELECTOR_NAME")
    public String dependencyName() {
        var componentDependency = dispatcherModel.getComponentDependency();
        return componentDependency.getName();
    }


    @FromTemplate("RECEIVER_METHOD_NAME")
    public String receiverMethodName() {
        return dispatcherModel.getMethodName();
    }

}
