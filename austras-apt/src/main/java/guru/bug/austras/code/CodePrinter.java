package guru.bug.austras.code;

import guru.bug.austras.code.common.PackageName;
import guru.bug.austras.code.common.QualifiedName;
import guru.bug.austras.code.decl.ImportDecl;
import org.apache.commons.text.StringEscapeUtils;

import java.io.PrintWriter;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class CodePrinter implements AutoCloseable {
    private final PrintWriter out;
    private final PackageName current;
    private final List<ImportDecl> imports;
    private final Deque<AttributesSnapshot> attributesStack = new LinkedList<>();
    private final Deque<Printable> printableStack = new LinkedList<>();
    private int lastCodePoint = '\n';

    public CodePrinter(PrintWriter out, PackageName current, List<ImportDecl> mutableImports) {
        this.out = out;
        this.current = current;
        this.imports = mutableImports;
    }

    public boolean checkImported(QualifiedName type) {
        if (type.getPackageName().isJavaLang()) {
            return true;
        }
        for (var i : imports) {
            if (i.isConflict(type)) {
                return false;
            } else if (i.isSame(type)) {
                return true;
            }
        }
        if (current.equals(type.getPackageName())) {
            return true;
        }
        var decl = ImportDecl.of(type);
        imports.add(decl);
        return true;
    }


    @Override
    public void close() {
        out.close();
    }

    public CodePrinter printAbstract() {
        return print("abstract");
    }

    public CodePrinter printContinue() {
        return print("continue");
    }

    public CodePrinter printFor() {
        return print("for");
    }

    public CodePrinter printNew() {
        return print("new");
    }

    public CodePrinter printSwitch() {
        return print("switch");
    }

    public CodePrinter printAssert() {
        return print("assert");
    }

    public CodePrinter printDefault() {
        return print("default");
    }

    public CodePrinter printIf() {
        return print("if");
    }

    public CodePrinter printPackage() {
        return print("package");
    }

    public CodePrinter printSynchronized() {
        return print("synchronized");
    }

    public CodePrinter printBoolean() {
        return print("boolean");
    }

    public CodePrinter printDo() {
        return print("do");
    }

    public CodePrinter printPrivate() {
        return print("private");
    }

    public CodePrinter printThis() {
        return print("this");
    }

    public CodePrinter printBreak() {
        return print("break");
    }

    public CodePrinter printDouble() {
        return print("double");
    }

    public CodePrinter printImplements() {
        return print("implements");
    }

    public CodePrinter printProtected() {
        return print("protected");
    }

    public CodePrinter printThrow() {
        return print("throw");
    }

    public CodePrinter printByte() {
        return print("byte");
    }

    public CodePrinter printElse() {
        return print("else");
    }

    public CodePrinter printImport() {
        return print("import");
    }

    public CodePrinter printPublic() {
        return print("public");
    }

    public CodePrinter printThrows() {
        return print("throws");
    }

    public CodePrinter printCase() {
        return print("case");
    }

    public CodePrinter printEnum() {
        return print("enum");
    }

    public CodePrinter printInstanceof() {
        return print("instanceof");
    }

    public CodePrinter printReturn() {
        return print("return");
    }

    public CodePrinter printTransient() {
        return print("transient");
    }

    public CodePrinter printCatch() {
        return print("catch");
    }

    public CodePrinter printExtends() {
        return print("extends");
    }

    public CodePrinter printInt() {
        return print("int");
    }

    public CodePrinter printShort() {
        return print("short");
    }

    public CodePrinter printTry() {
        return print("try");
    }

    public CodePrinter printChar() {
        return print("char");
    }

    public CodePrinter printFinal() {
        return print("final");
    }

    public CodePrinter printInterface() {
        return print("interface");
    }

    public CodePrinter printStatic() {
        return print("static");
    }

    public CodePrinter printVoid() {
        return print("void");
    }

    public CodePrinter printClass() {
        return print("class");
    }

    public CodePrinter printFinally() {
        return print("finally");
    }

    public CodePrinter printLong() {
        return print("long");
    }

    public CodePrinter printStrictfp() {
        return print("strictfp");
    }

    public CodePrinter printVolatile() {
        return print("volatile");
    }

    public CodePrinter printConst() {
        return print("const");
    }

    public CodePrinter printFloat() {
        return print("float");
    }

    public CodePrinter printNative() {
        return print("native");
    }

    public CodePrinter printSuper() {
        return print("super");
    }

    public CodePrinter printWhile() {
        return print("while");
    }

    public CodePrinter printVar() {
        return print("var");
    }

    public CodePrinter printNull() {
        return print("null");
    }

    public CodePrinter printTrue() {
        return print("true");
    }

    public CodePrinter printFalse() {
        return print("false");
    }

    public CodePrinter print(Printable printable) {
        if (printable == null) {
            return this;
        }
        weakPrefix();
        separator();
        attributesStack.push(new AttributesSnapshot(withAttributes().absoluteIndent(getCurrentIndent())));
        printableStack.push(printable);
        printable.print(this);
        printableStack.pop();
        attributesStack.pop();
        markPrinted();
        return this;
    }

    public <T> T find(Class<T> clazz) {
        //noinspection unchecked
        return (T) printableStack.stream().filter(p -> clazz.isAssignableFrom(p.getClass())).findFirst().orElse(null);
    }

    private void weakPrefix() {
        Optional.ofNullable(attributesStack.peek())
                .filter(a -> a.weakPrefix != null && !a.printed)
                .ifPresent(a -> acceptPart(a.weakPrefix));

    }

    private void markPrinted() {
        Optional.ofNullable(attributesStack.peek())
                .ifPresent(a -> a.printed = true);

    }

    public CodePrinter print(List<? extends Printable> printables) {
        if (printables == null || printables.isEmpty()) {
            return this;
        }
        for (var w : printables) {
            print(w);
        }
        return this;
    }

    public CodePrinter print(String str) {
        print(out -> out.print0(str));
        return this;
    }

    private void separator() {
        Optional.ofNullable(attributesStack.peek())
                .ifPresent(attr -> {
                    if (attr.separator != null && attr.needSeparator) {
                        acceptPart(attr.separator);
                    }
                    attr.needSeparator = true;
                });
    }

    private void acceptPart(Consumer<CodePrinter> part) {
        attributesStack.push(new AttributesSnapshot(withAttributes().absoluteIndent(getCurrentIndent())));
        part.accept(this);
        attributesStack.pop();
    }

    private CodePrinter print0(String str) {
        var first = str.codePointAt(0);
        if (Character.isLetterOrDigit(first) && Character.isLetterOrDigit(lastCodePoint)) {
            space();
        }
        str.codePoints().forEach(this::print);
        return this;
    }

    public CodePrinter printLiteral(String str) {
        print0("\"");
        print0(StringEscapeUtils.escapeJava(str));
        print0("\"");
        return this;
    }

    private void print(int codePoint) {
        ensureIdent();
        out.print(Character.toString(codePoint));
        lastCodePoint = codePoint;
    }

    public CodePrinter print(Attributes attributes, Consumer<CodePrinter> out) {
        var snapshot = new AttributesSnapshot(attributes);
        if (snapshot.prefix != null) {
            acceptPart(snapshot.prefix);
        }
        if (!snapshot.absoluteIndent) {
            snapshot.actualIdent += getCurrentIndent();
        }
        attributesStack.push(snapshot);
        out.accept(this);
        attributesStack.pop();
        if (snapshot.printed && snapshot.weakSuffix != null) {
            acceptPart(snapshot.weakSuffix);
        }
        if (snapshot.suffix != null) {
            acceptPart(snapshot.suffix);
        }
        if (!snapshot.printed && snapshot.empty != null) {
            acceptPart(snapshot.empty);
        }
        return this;
    }

    public Attributes withAttributes() {
        return new Attributes();
    }

    private Integer getCurrentIndent() {
        return Optional.ofNullable(attributesStack.peek())
                .map(a -> a.actualIdent)
                .orElse(0);
    }

    public CodePrinter space() {
        if (Character.isWhitespace(lastCodePoint)) {
            return this;
        }
        out.print(' ');
        lastCodePoint = ' ';
        return this;
    }

    private void ensureIdent() {
        var indent = getCurrentIndent();
        if (lastCodePoint == '\n' && indent > 0) {
            for (int i = 0; i < indent; i++) {
                out.print(" ");
            }
            lastCodePoint = ' ';
        }
    }

    public static class Attributes {
        private Consumer<CodePrinter> suffix;
        private Consumer<CodePrinter> prefix;
        private Consumer<CodePrinter> weakPrefix;
        private Consumer<CodePrinter> weakSuffix;
        private Consumer<CodePrinter> separator;
        private Consumer<CodePrinter> empty;
        private int indent;
        private boolean absoluteIndent;

        public Attributes prefix(String s) {
            return prefix(o -> o.print(s));
        }

        public Attributes prefix(Consumer<CodePrinter> s) {
            this.prefix = s;
            return this;
        }

        public Attributes suffix(String s) {
            return suffix(o -> o.print(s));
        }

        public Attributes suffix(Consumer<CodePrinter> s) {
            this.suffix = s;
            return this;
        }

        public Attributes weakPrefix(String s) {
            return weakPrefix(o -> o.print(s));
        }

        public Attributes weakPrefix(Consumer<CodePrinter> s) {
            this.weakPrefix = s;
            return this;
        }

        public Attributes weakSuffix(String s) {
            return weakSuffix(o -> o.print(s));
        }

        public Attributes weakSuffix(Consumer<CodePrinter> s) {
            this.weakSuffix = s;
            return this;
        }

        public Attributes separator(String s) {
            return separator(o -> o.print(s));
        }

        public Attributes separator(Consumer<CodePrinter> s) {
            this.separator = s;
            return this;
        }

        public Attributes empty(String s) {
            return empty(o -> o.print(s));
        }

        public Attributes empty(Consumer<CodePrinter> s) {
            this.empty = s;
            return this;
        }

        public Attributes indent(int relative) {
            absoluteIndent = false;
            this.indent = relative;
            return this;
        }

        public Attributes absoluteIndent(int absolute) {
            absoluteIndent = true;
            this.indent = absolute;
            return this;
        }

    }

    private static class AttributesSnapshot {
        private final Consumer<CodePrinter> suffix;
        private final Consumer<CodePrinter> prefix;
        private final Consumer<CodePrinter> weakSuffix;
        private final Consumer<CodePrinter> weakPrefix;
        private final Consumer<CodePrinter> separator;
        private final Consumer<CodePrinter> empty;
        private final int indent;
        private final boolean absoluteIndent;

        private int actualIdent;
        private boolean needSeparator = false;
        private boolean printed = false;

        private AttributesSnapshot(Attributes attributes) {
            this.suffix = attributes.suffix;
            this.prefix = attributes.prefix;
            this.weakPrefix = attributes.weakPrefix;
            this.weakSuffix = attributes.weakSuffix;
            this.separator = attributes.separator;
            this.empty = attributes.empty;
            this.indent = attributes.indent;
            this.actualIdent = attributes.indent;
            this.absoluteIndent = attributes.absoluteIndent;
        }
    }
}
