package guru.bug.austras.code.common;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CodeLine implements Printable {
    private static final CodeLine emptyLine = raw("");
    private final List<Printable> line;

    private CodeLine(List<Printable> line) {
        this.line = line;
    }

    public static CodeLine raw(String line) {
        RawSegment segment = new RawSegment(line);
        return new CodeLine(List.of(segment));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static CodeLine emptyLine() {
        return emptyLine;
    }

    @Override
    public void print(CodePrinter out) {
        out.print(line);
    }

    public static class Builder {
        private List<Printable> line;

        private List<Printable> line() {
            if (line == null) {
                line = new ArrayList<>();
            }
            return line;
        }

        public Builder raw(String segment) {
            RawSegment s = new RawSegment(segment);
            line().add(s);
            return this;
        }

        public Builder add(Printable printable) {
            line().add(printable);
            return this;
        }

        public Builder print(Consumer<CodePrinter> printable) {
            line().add(new DeferredPrintable(printable));
            return this;
        }

        public CodeLine build() {
            return new CodeLine(line == null ? null : List.copyOf(line));
        }
    }

    private static class RawSegment implements Printable {
        private final String raw;

        private RawSegment(String raw) {
            this.raw = raw;
        }

        @Override
        public void print(CodePrinter out) {
            out.print(raw);
        }
    }

    private static class DeferredPrintable implements Printable {
        private final Consumer<CodePrinter> printable;

        private DeferredPrintable(Consumer<CodePrinter> printable) {
            this.printable = printable;
        }

        @Override
        public void print(CodePrinter out) {
            printable.accept(out);
        }
    }
}
