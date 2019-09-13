package guru.bug.austras.codegen.common;

import guru.bug.austras.codegen.CodePrinter;
import guru.bug.austras.codegen.Printable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        out.print(out.withSeparator("\n"), o -> o.print(lines));
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

        public Builder addLines(Collection<CodeLine> lines) {
            lines().addAll(lines);
            return this;
        }

        public CodeBlock build() {
            return new CodeBlock(lines == null ? null : List.copyOf(lines.stream().filter(Objects::nonNull).collect(Collectors.toList())));
        }

        public Builder addLine(CodeLine line) {
            lines().add(line);
            return this;
        }
    }
}
