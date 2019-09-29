package guru.bug.austras.engine;

import guru.bug.austras.codegen.CompilationUnit;

import java.io.IOException;

public interface FileManager {
    void createFile(CompilationUnit compilationUnit) throws IOException;
}
