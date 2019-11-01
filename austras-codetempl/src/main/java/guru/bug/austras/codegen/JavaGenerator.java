package guru.bug.austras.codegen;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public abstract class JavaGenerator extends Generator {
    private Map<String, ImportLine> imports = new HashMap<>();

    protected JavaGenerator() throws IOException {
        super();
    }

    protected final String tryImport(String qualifiedName) {
        var idx = qualifiedName.lastIndexOf(".");
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
        var idx = qualifiedName.lastIndexOf(".");
        var pkg = qualifiedName.substring(0, idx);
        return isPrintableToImportPkg(pkg);
    }

    private boolean isPrintableToImportPkg(String pkg) {
        return !pkg.equals("java.lang") && !pkg.equals(getPackageName());
    }

    @Override
    public String generateToString() {
        super.generateToString(); // ignoring result just to fill imports;
        return super.generateToString();
    }

    public abstract String getPackageName();

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
                        out.println(";");
                    });
        } else {
            imports.values().stream()
                    .filter(i -> isPrintableToImportPkg(i.packageName))
                    .sorted()
                    .forEach(s -> {
                        out.print("import ");
                        out.print(s.packageName);
                        out.print(".");
                        out.print(s.className);
                        out.println(";");
                    });
        }
    }

    private static class ImportLine implements Comparable<ImportLine> {
        String packageName;
        String className;

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
