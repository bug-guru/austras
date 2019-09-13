package guru.bug.austras.codegen.decl;

import guru.bug.austras.codegen.CodePrinter;
import guru.bug.austras.codegen.Printable;

import java.util.function.Consumer;

public enum Modifier implements Printable {
    PUBLIC(CodePrinter::printPublic),
    PROTECTED(CodePrinter::printProtected),
    PRIVATE(CodePrinter::printPrivate),
    ABSTRACT(CodePrinter::printAbstract),
    STATIC(CodePrinter::printStatic),
    FINAL(CodePrinter::printFinal),
    STRICTFP(CodePrinter::printStrictfp),
    SYNCHRONIZED(CodePrinter::printSynchronized),
    NATIVE(CodePrinter::printNative),
    TRANSIENT(CodePrinter::printTransient),
    VOLATILE(CodePrinter::printVolatile);

    private final Consumer<CodePrinter> modifierPrinter;

    Modifier(Consumer<CodePrinter> modifierPrinter) {

        this.modifierPrinter = modifierPrinter;
    }

    @Override
    public void print(CodePrinter out) {
        modifierPrinter.accept(out);
    }
}
