package guru.bug.austras.codegen;

import javax.annotation.processing.Filer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public abstract class JavaGenerator extends Generator {
    private final Map<String, ImportLine> imports = new HashMap<>();
    private final Filer filer;

    protected JavaGenerator(Filer filer) throws IOException, TemplateException {
        super();
        this.filer = filer;
    }

    protected final String tryImport(String typeName) {
        var tpFirstIdx = typeName.indexOf('<');
        if (tpFirstIdx == -1) {
            return tryImport0(typeName);
        } else {
            var type = tryImport0(typeName.substring(0, tpFirstIdx));
            var tpLastIdx = typeName.lastIndexOf('>');
            if (tpLastIdx == -1) {
                throw new IllegalArgumentException("wrong type " + typeName);
            }
            var param = tryImport(typeName.substring(tpFirstIdx + 1, tpLastIdx));
            return type + '<' + param + '>';
        }
    }

    private String tryImport0(String qualifiedName) {
        var idx = qualifiedName.lastIndexOf('.');
        var pkg = qualifiedName.substring(0, idx);
        var cls = qualifiedName.substring(idx + 1);
        var imp = imports.get(cls);
        if (imp == null) {
            imp = new ImportLine();
            imp.className = cls;
            imp.packageName = pkg;
            imports.put(cls, imp);
            return cls;
        } else if (pkg.equals(imp.packageName)) {
            return cls;
        } else {
            return qualifiedName;
        }
    }

    private boolean isPrintableToImports(String qualifiedName) {
        var idx = qualifiedName.lastIndexOf('.');
        var pkg = qualifiedName.substring(0, idx);
        return isPrintableToImportPkg(pkg);
    }

    private boolean isPrintableToImportPkg(String pkg) {
        return !pkg.equals("java.lang") && !pkg.equals(getPackageName());
    }

    protected final void generateJavaClass() {
        imports.clear();
        var qualifiedName = getPackageName() + '.' + getSimpleClassName();
        try (var out = filer.createSourceFile(qualifiedName).openWriter()) {
            super.generateToString(); // ignoring result just to fill imports
            super.generateTo(out);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    @SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
    public abstract String getPackageName();

    @SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
    public abstract String getSimpleClassName();

    @FromTemplate("IMPORTS")
    public final void processImports(PrintWriter out, BodyBlock body) {
        var strBody = body.evaluateBody();
        if (imports.isEmpty()) {
            strBody.lines()
                    .filter(s -> !s.isBlank())
                    .filter(this::isPrintableToImports)
                    .forEach(s -> {
                        tryImport(s);
                        out.print("import ");
                        out.print(s);
                        out.println(';');
                    });
        } else {
            imports.values().stream()
                    .filter(i -> isPrintableToImportPkg(i.packageName))
                    .sorted()
                    .forEach(s -> {
                        out.print("import ");
                        out.print(s.packageName);
                        out.print('.');
                        out.print(s.className);
                        out.println(';');
                    });
        }
    }

    private static class ImportLine implements Comparable<ImportLine> {
        String packageName;
        String className;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ImportLine that = (ImportLine) o;

            if (!packageName.equals(that.packageName)) return false;
            return className.equals(that.className);
        }

        @Override
        public int hashCode() {
            int result = packageName.hashCode();
            result = 31 * result + className.hashCode();
            return result;
        }

        @Override
        public int compareTo(ImportLine o) {
            int result = this.packageName.compareTo(o.packageName);
            if (result == 0) {
                return this.className.compareTo(o.className);
            }
            return result;
        }
    }
}