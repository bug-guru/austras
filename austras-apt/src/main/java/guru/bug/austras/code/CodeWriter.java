package guru.bug.austras.code;

import guru.bug.austras.code.decl.ImportDecl;
import guru.bug.austras.code.name.PackageName;
import guru.bug.austras.code.name.QualifiedName;

import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

public class CodeWriter implements AutoCloseable {
    private final PrintWriter out;
    private final PackageName current;
    private final List<ImportDecl> imports;

    public CodeWriter(PrintWriter out, PackageName current, List<ImportDecl> mutableImports) {
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

    public void write(int c) {
        out.write(c);
    }

    public void write(char[] buf, int off, int len) {
        out.write(buf, off, len);
    }

    public void write(char[] buf) {
        out.write(buf);
    }

    public void write(String s, int off, int len) {
        out.write(s, off, len);
    }

    public void write(String s) {
        out.write(s);
    }

    public void print(boolean b) {
        out.print(b);
    }

    public void print(char c) {
        out.print(c);
    }

    public void print(int i) {
        out.print(i);
    }

    public void print(long l) {
        out.print(l);
    }

    public void print(float f) {
        out.print(f);
    }

    public void print(double d) {
        out.print(d);
    }

    public void print(char[] s) {
        out.print(s);
    }

    public void print(String s) {
        out.print(s);
    }

    public void print(Object obj) {
        out.print(obj);
    }

    public void println() {
        out.println();
    }

    public void println(boolean x) {
        out.println(x);
    }

    public void println(char x) {
        out.println(x);
    }

    public void println(int x) {
        out.println(x);
    }

    public void println(long x) {
        out.println(x);
    }

    public void println(float x) {
        out.println(x);
    }

    public void println(double x) {
        out.println(x);
    }

    public void println(char[] x) {
        out.println(x);
    }

    public void println(String x) {
        out.println(x);
    }

    public void println(Object x) {
        out.println(x);
    }

    public PrintWriter printf(String format, Object... args) {
        return out.printf(format, args);
    }

    public PrintWriter printf(Locale l, String format, Object... args) {
        return out.printf(l, format, args);
    }

    public PrintWriter format(String format, Object... args) {
        return out.format(format, args);
    }

    public PrintWriter format(Locale l, String format, Object... args) {
        return out.format(l, format, args);
    }

    public PrintWriter append(CharSequence csq) {
        return out.append(csq);
    }

    public PrintWriter append(CharSequence csq, int start, int end) {
        return out.append(csq, start, end);
    }

    public PrintWriter append(char c) {
        return out.append(c);
    }

    @Override
    public void close() {
        out.close();
    }

    public void write(Writable writable) {
        writable.write(this);
    }
}
