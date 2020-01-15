/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.javacode;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class ImportsManager {
    private final Map<String, ImportLine> imports = new HashMap<>();
    private final String packageName;

    ImportsManager(String packageName) {
        this.packageName = packageName;
    }

    void processImports(PrintWriter out, Set<String> importedLined) {
        if (imports.isEmpty()) {
            importedLined.stream()
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

    String tryImport(String typeName) {
        String prefix = "";
        if (typeName.startsWith("? extends ")) {
            prefix = "? extends ";
        }
        var tpFirstIdx = typeName.indexOf('<');
        if (tpFirstIdx == -1) {
            return prefix + tryImport0(typeName.substring(prefix.length()));
        } else {
            var type = tryImport0(typeName.substring(prefix.length(), tpFirstIdx));
            var tpLastIdx = typeName.lastIndexOf('>');
            if (tpLastIdx == -1) {
                throw new IllegalArgumentException("wrong type " + typeName);
            }
            var param = tryImport(typeName.substring(tpFirstIdx + 1, tpLastIdx));
            return prefix + type + '<' + param + '>';
        }
    }

    private String tryImport0(String qualifiedName) {
        var idx = qualifiedName.lastIndexOf('.');
        var pkg = idx == -1 ? "" : qualifiedName.substring(0, idx);
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
        if (idx == -1) {
            return true;
        }
        var pkg = qualifiedName.substring(0, idx);
        return isPrintableToImportPkg(pkg);
    }

    private boolean isPrintableToImportPkg(String pkg) {
        return !pkg.equals("java.lang") && !pkg.equals(packageName);
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
