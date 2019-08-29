package guru.bug.austras.code;

import guru.bug.austras.code.decl.ImportDecl;
import guru.bug.austras.code.name.PackageName;
import guru.bug.austras.code.name.QualifiedName;
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
    private final Deque<PrintingAttr> attributes = new LinkedList<>();
    private int lastCodePoint = '\n';

    public CodePrinter(PrintWriter out, PackageName current, List<ImportDecl> mutableImports) {
        this.out = out;
        this.current = current;
        this.imports = mutableImports;
    }

    public boolean checkImported(QualifiedName type) {
        if (type.getPackageName().isJavaLang())
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

    public CodePrinter print(Printable printable) {
        if (printable == null) {
            return this;
        }
        attributes.push(new PrintingAttr(null, getCurrentIndent()));
        printable.print(this);
        attributes.pop();
        return this;
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

    public CodePrinter print(String str) {
        Optional.ofNullable(attributes.peek())
                .ifPresent(attr -> {
                    if (attr.separator != null && attr.needSeparator) {
                        print0(attr.separator);
                    }
                    attr.needSeparator = true;
                });
        print0(str);
        return this;
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

    public CodePrinter print(String prefix, String suffix, String separator, Consumer<CodePrinter> out) {
        return indent(0, prefix, suffix, separator, out);
    }

    public CodePrinter print(String separator, Consumer<CodePrinter> out) {
        return indent(0, null, null, separator, out);
    }

    public CodePrinter print(String prefix, String suffix, Consumer<CodePrinter> out) {
        return indent(0, prefix, suffix, null, out);
    }

    public CodePrinter indent(String prefix, String suffix, Consumer<CodePrinter> out) {
        return indent(prefix, suffix, null, out);
    }

    public CodePrinter indent(Consumer<CodePrinter> out) {
        return indent(null, null, null, out);
    }

    public CodePrinter indent(String prefix, String suffix, String separator, Consumer<CodePrinter> out) {
        return indent(1, prefix, suffix, separator, out);
    }

    public CodePrinter indent(String separator, Consumer<CodePrinter> out) {
        return indent(1, null, separator, separator, out);
    }

    public CodePrinter indent(int extraIndent, String prefix, String suffix, String separator, Consumer<CodePrinter> out) {
        if (prefix != null) {
            print0(prefix);
        }
        var indent = getCurrentIndent();
        attributes.push(new PrintingAttr(separator, indent + extraIndent));
        out.accept(this);
        attributes.pop();
        if (suffix != null) {
            print0(suffix);
        }
        return this;
    }

    private Integer getCurrentIndent() {
        return Optional.ofNullable(attributes.peek())
                .map(a -> a.indent)
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
                out.print("    ");
            }
            lastCodePoint = ' ';
        }
    }

    private static class PrintingAttr {
        private final String separator;
        private final int indent;
        private boolean needSeparator = false;

        private PrintingAttr(String separator, int indent) {
            this.separator = separator;
            this.indent = indent;
        }
    }
}
