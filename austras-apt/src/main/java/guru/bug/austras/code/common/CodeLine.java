package guru.bug.austras.code.common;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;

public class CodeLine implements Printable {
    private final String line;

    private CodeLine(String line) {
        this.line = line;
    }

    public static CodeLine raw(String line) {
        return new CodeLine(line);
    }

    @Override
    public void print(CodePrinter out) {
        out.print(line);
    }
}
