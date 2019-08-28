package guru.bug.austras.code.decl;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;

import java.util.function.Consumer;

public enum ClassModifier implements Printable {
    PUBLIC(CodePrinter::printPublic),
    PROTECTED(CodePrinter::printProtected),
    PRIVATE(CodePrinter::printPrivate),
    ABSTRACT(CodePrinter::printAbstract),
    STATIC(CodePrinter::printStatic),
    FINAL(CodePrinter::printFinal),
    STRICTFP(CodePrinter::printStrictfp);

    private final Consumer<CodePrinter> modifierPrinter;

    ClassModifier(Consumer<CodePrinter> modifierPrinter) {

        this.modifierPrinter = modifierPrinter;
    }

    @Override
    public void print(CodePrinter out) {
        modifierPrinter.accept(out);
    }
}
