/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

class ImportsManager {
    private final Map<String, ImportLine> imports = new HashMap<>();
    private String packageName;

    void processImports(PrintWriter out) {
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

    void requireImport(String typeName) {
        tryImport(typeName, true);
    }

    String tryImport(String typeName) {
        return tryImport(typeName, false);
    }

    private String tryImport(String typeName, boolean required) {
        String prefix = "";
        if (typeName.startsWith("? extends ")) {
            prefix = "? extends ";
        }
        var tpFirstIdx = typeName.indexOf('<');
        if (tpFirstIdx == -1) {
            return prefix + tryImport0(typeName.substring(prefix.length()), required);
        } else {
            var type = tryImport0(typeName.substring(prefix.length(), tpFirstIdx), required);
            var tpLastIdx = typeName.lastIndexOf('>');
            if (tpLastIdx == -1) {
                throw new IllegalArgumentException("wrong type " + typeName);
            }
            var param = tryImport(typeName.substring(tpFirstIdx + 1, tpLastIdx), required);
            return prefix + type + '<' + param + '>';
        }
    }

    private String tryImport0(String qualifiedName, boolean required) {
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
        } else if (required) {
            throw new IllegalArgumentException(qualifiedName + " cannot be imported: conflict");
        } else {
            return qualifiedName;
        }
    }

    private boolean isPrintableToImportPkg(String pkg) {
        return !pkg.equals("java.lang") && !pkg.equals(packageName);
    }

    void initPackage(String packageName) {
        clear();
        this.packageName = packageName;
    }

    void clear() {
        imports.clear();
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
