package guru.bug.austras.apt.core.generators;

import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.core.componentmap.ComponentKey;
import guru.bug.austras.apt.core.componentmap.ComponentMap;
import guru.bug.austras.apt.core.componentmap.UniqueNameGenerator;
import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.DependencyModel;
import guru.bug.austras.apt.model.ProviderModel;
import guru.bug.austras.codegen.CompilationUnit;
import guru.bug.austras.codegen.common.CodeBlock;
import guru.bug.austras.codegen.common.CodeLine;
import guru.bug.austras.codegen.common.QualifiedName;
import guru.bug.austras.codegen.decl.ClassMemberDecl;
import guru.bug.austras.codegen.decl.MethodParamDecl;
import guru.bug.austras.codegen.decl.PackageDecl;
import guru.bug.austras.codegen.decl.TypeDecl;
import guru.bug.austras.codegen.spec.TypeArg;
import guru.bug.austras.codegen.spec.TypeSpec;
import guru.bug.austras.provider.CollectionProvider;
import guru.bug.austras.startup.StartupServicesStarter;
import org.apache.commons.text.StringEscapeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class MainClassGenerator {
    private static final Logger log = Logger.getLogger(MainClassGenerator.class.getName());
    private final UniqueNameGenerator uniqueNameGenerator;
    private final ProcessingEnvironment processingEnv;
    private final ComponentMap componentMap;
    private final Elements elementUtils;
    private final String loggerVarName;
    private final ModelUtils modelUtils;
    private ComponentModel appComponentModel;

    public MainClassGenerator(ProcessingEnvironment processingEnv, ComponentMap componentMap, UniqueNameGenerator uniqueNameGenerator, ModelUtils modelUtils) {
        this.processingEnv = processingEnv;
        this.componentMap = componentMap;
        this.elementUtils = processingEnv.getElementUtils();
        this.uniqueNameGenerator = uniqueNameGenerator;
        this.loggerVarName = uniqueNameGenerator.findFreeVarName("log");
        this.modelUtils = modelUtils;
    }

    public void setAppComponentModel(ComponentModel appComponentModel) {
        if (this.appComponentModel != null) {
            throw new IllegalStateException("Application already defined");
        }
        this.appComponentModel = appComponentModel;
    }

    public void generateAppMain() throws IOException {
        if (appComponentModel == null) {
            log.fine("No application component");
            return;
        }
        var sortedComponents = sortComponents();
        var appInstantiable = appComponentModel.getInstantiable();
        var mainClassQualifiedName = appInstantiable + "Main";
        var lastDotIndex = mainClassQualifiedName.lastIndexOf('.');
        var mainClassSimpleName = mainClassQualifiedName.substring(lastDotIndex + 1);
        var packageName = mainClassQualifiedName.substring(0, lastDotIndex);

        var unit = CompilationUnit.builder()
                .packageDecl(PackageDecl.of(packageName))
                .addTypeDecl(TypeDecl.classBuilder()
                        .publicMod()
                        .simpleName(mainClassSimpleName)
                        .addMember(ClassMemberDecl.fieldBulder(TypeSpec.of(QualifiedName.of(Logger.class)), loggerVarName)
                                .publicMod()
                                .staticMod()
                                .finalMod()
                                .initializer(CodeBlock.builder()
                                        .addLine(format("java.util.logging.Logger.getLogger(\"%s\")", mainClassQualifiedName))
                                        .build())
                                .build())
                        .addMember(ClassMemberDecl.methodBuilder("main", TypeSpec.voidType())
                                .publicMod()
                                .staticMod()
                                .addParam(MethodParamDecl.builder()
                                        .type(TypeSpec.builder()
                                                .name(QualifiedName.of(String.class))
                                                .array()
                                                .build())
                                        .name("args")
                                        .build())
                                .body(createMainBody(sortedComponents))
                                .build())
                        .build())
                .build();


        try (var file = processingEnv.getFiler().createSourceFile(unit.getQualifiedName()).openWriter();
             var out = new PrintWriter(file)) {
            unit.print(out);
        }
    }

    private CodeBlock createMainBody(List<ComponentModel> sortedComponents) {
        return CodeBlock.builder()
                .addLine(genInfoLog("Application is initializing..."))
                .addLines(generateProviderCalls(sortedComponents))
                .addLine(CodeLine.emptyLine())
                .addLine(genInfoLog("Starting up services..."))
                .addLines(generateServicesCall())
                .addLine(genInfoLog("Services are started!"))
                .addLine(CodeLine.emptyLine())
                .addLine(genInfoLog("Application is ready!"))
                .build();
    }

    private Collection<CodeLine> generateProviderCalls(List<ComponentModel> sortedComponents) {
        return sortedComponents.stream()
                .flatMap(m -> generateProviderCall(m).stream())
                .collect(Collectors.toList());
    }

    private List<CodeLine> generateServicesCall() {
        var result = new ArrayList<CodeLine>();
        var key = new ComponentKey(StartupServicesStarter.class.getName(), null);
        var starter = componentMap.findSingleComponentModel(key);
        var starterType = TypeSpec.of(starter.getInstantiable());
        result.add(CodeLine.builder()
                .print(o -> {
                    o.print(starterType);
                    o.space();
                    o.print(starter.getName());
                    o.print(" = ");
                    o.print(starter.getProvider().getName());
                    o.print(".get();");
                })
                .build());
        result.add(CodeLine.builder()
                .raw(starter.getName())
                .raw(".initAll();")
                .build());
        return result;
    }

    private CodeLine genInfoLog(String msg) {
        return CodeLine.raw(format("%s.info(\"%s\");", loggerVarName, StringEscapeUtils.escapeJava(msg)));
    }

    private List<CodeLine> generateProviderCall(ComponentModel componentModel) {
        log.fine(() -> "Generating provider call of model " + componentModel);
        var result = new ArrayList<CodeLine>();
        result.add(CodeLine.emptyLine());
        ProviderModel provider = componentModel.getProvider();
        result.add(genInfoLog("Initializing provider " + provider.getInstantiable() + " for " + componentModel.getInstantiable()));
        var initializers = provider.getDependencies().stream()
                .map(this::createInitializer)
                .peek(i -> result.add(i.init()))
                .collect(Collectors.toList());
        var params = initializers.stream()
                .map(ParamInitializer::getAsParameter)
                .collect(Collectors.joining(", "));
        var providerType = TypeSpec.of(provider.getInstantiable());
        result.add(CodeLine.builder()
                .print(o -> {
                    o.print(providerType);
                    o.space();
                    o.print(provider.getName());
                    o.print(" = ");
                    o.printNew();
                    o.print(providerType);
                    o.print("(");
                    o.print(params);
                    o.print(");");
                })
                .build());
        return result;
    }

    private List<ComponentModel> sortComponents() {
        var result = new ArrayList<ComponentModel>();
        var unresolved = new LinkedList<>(componentMap.getKeys());
        var resolved = Collections.<ComponentModel>newSetFromMap(new IdentityHashMap<>());
        outer:
        while (!unresolved.isEmpty()) {
            var key = unresolved.remove();
            var components = Collections.<ComponentModel>newSetFromMap(new IdentityHashMap<>());
            components.addAll(componentMap.findComponentModels(key));
            for (var comp : components) {
                ProviderModel provider = Objects.requireNonNull(comp.getProvider(), () -> format("Component %s doesn't have a provider", comp));
                List<DependencyModel> dependencies = Objects.requireNonNull(provider.getDependencies());
                var hasUnresolved = dependencies.stream()
                        .map(d -> new ComponentKey(d.getType(), d.getQualifiers()))
                        .anyMatch(unresolved::contains);
                if (hasUnresolved) {
                    unresolved.add(key);
                    continue outer;
                }
            }
            components.removeAll(resolved);
            resolved.addAll(components);
            result.addAll(components);
        }
        return result;
    }


    private ParamInitializer createInitializer(DependencyModel model) {
        ParamInitializer result;
        if (model.isCollection()) {
            result = new CollectionParamInitializer(model);
        } else {
            result = new StandardParamInitializer(model);
        }
        if (!model.isProvider()) {
            result = new ProviderUnwrappedParamInitializer(result);
        }
        return result;
    }

    private interface ParamInitializer {
        CodeLine init();

        String getAsParameter();
    }

    private class ProviderUnwrappedParamInitializer implements ParamInitializer {
        private final ParamInitializer initializer;

        ProviderUnwrappedParamInitializer(ParamInitializer initializer) {
            this.initializer = initializer;
        }

        @Override
        public CodeLine init() {
            return initializer.init();
        }

        @Override
        public String getAsParameter() {
            return initializer.getAsParameter() + ".get()";
        }
    }

    private abstract class AbstractParamInitializer implements ParamInitializer {
        final DependencyModel dependencyModel;
        final ComponentKey key;

        AbstractParamInitializer(DependencyModel dependencyModel) {
            this.dependencyModel = dependencyModel;
            this.key = new ComponentKey(dependencyModel.getType(), dependencyModel.getQualifiers());
        }

        @Override
        public CodeLine init() {
            return null;
        }

    }

    private class StandardParamInitializer extends AbstractParamInitializer {

        StandardParamInitializer(DependencyModel dependencyModel) {
            super(dependencyModel);
        }

        @Override
        public String getAsParameter() {
            ComponentModel singleComponentModel = componentMap.findSingleComponentModel(key);
            if (singleComponentModel == null) {
                throw new IllegalStateException("Component " + key + " not found"); // TODO
            }
            ProviderModel provider = singleComponentModel.getProvider();
            if (provider == null) {
                throw new IllegalStateException("Provider not found for component " + key + " not found"); // TODO
            }
            return provider.getName();
        }
    }

    private class CollectionParamInitializer extends AbstractParamInitializer {
        final String name;

        CollectionParamInitializer(DependencyModel dependencyModel) {
            super(dependencyModel);
            this.name = uniqueNameGenerator.findFreeVarName(dependencyModel.getName() + "Collection");
        }

        @Override
        public CodeLine init() {
            var varType = modelUtils.convertToParameterType(dependencyModel);
            var params = componentMap.findComponentModels(key);
            return CodeLine.builder()
                    .print(o -> {
                        o.print(varType);
                        o.space();
                        o.print(name);
                        o.print(" = ");
                        o.printNew();
                        o.print(TypeSpec.of(CollectionProvider.class, TypeArg.diamond()));
                        o.print("(");
                        o.print(params.stream()
                                .map(c -> c.getProvider().getName())
                                .collect(Collectors.joining(", ")));
                        o.print(");");
                    })
                    .build();
        }

        @Override
        public String getAsParameter() {
            return name;
        }
    }

}
