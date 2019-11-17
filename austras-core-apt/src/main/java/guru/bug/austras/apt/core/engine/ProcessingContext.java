package guru.bug.austras.apt.core.engine;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;

public interface ProcessingContext {
    ProcessingEnvironment processingEnv();

    RoundEnvironment roundEnv();

    ComponentManager componentManager();
}
