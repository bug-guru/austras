package guru.bug.austras.code.common;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;

import java.util.ArrayList;
import java.util.List;

public class CodeBlock implements Printable {
    private final List<CodeLine> lines;

    private CodeBlock(List<CodeLine> lines) {
        this.lines = lines;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void print(CodePrinter out) {
        out.print(out.withSeparator("\n").suffix("\n"), o -> o.print(lines));
    }

    public static class Builder {
        private List<CodeLine> lines;

        private List<CodeLine> lines() {
            if (lines == null) {
                lines = new ArrayList<>();
            }
            return lines;
        }

        public Builder addLine(String line) {
            lines().add(CodeLine.raw(line));
            return this;
        }

        public CodeBlock build() {
            return new CodeBlock(lines == null ? null : List.copyOf(lines));
        }
    }
}
