/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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

    private String tryImport(String type, boolean required) {
        var trimmedType = type.strip();
        var pStartIdx = trimmedType.indexOf('<');
        if (pStartIdx == -1) {
            return new TypePart(tryImport0(trimmedType, required)).toString();
        }
        var name = tryImport0(trimmedType.substring(0, pStartIdx), required);
        var params = new ArrayList<TypePart>(2);
        parseParams(trimmedType, pStartIdx, params, required);
        return new TypePart(name, params).toString();
    }

    private int parseParams(String type, int fromIdx, List<TypePart> result, boolean required) {
        var startIdx = fromIdx + 1;
        while (true) {
            var commaIdx = type.indexOf(',', startIdx);
            var paramStartIdx = type.indexOf('<', startIdx);
            var paramEndIdx = type.indexOf('>', startIdx);
            var minIdx = IntStream.of(commaIdx, paramStartIdx, paramEndIdx).filter(i -> i >= 0).min().orElseThrow();
            if (minIdx == paramEndIdx) {
                var paramTypeName = type.substring(startIdx, minIdx).strip();
                if (!paramTypeName.isEmpty()) {
                    result.add(new TypePart(tryImport0(paramTypeName, required)));
                }
                startIdx = minIdx;
                break;
            }
            if (minIdx == commaIdx) {
                var paramTypeName = type.substring(startIdx, minIdx).strip();
                if (!paramTypeName.isEmpty()) {
                    result.add(new TypePart(tryImport0(paramTypeName, required)));
                }
                startIdx = minIdx + 1;
            }
            if (minIdx == paramStartIdx) {
                var paramTypeName = tryImport0(type.substring(startIdx, minIdx).strip(), required);
                var params = new ArrayList<TypePart>(2);
                result.add(new TypePart(paramTypeName, params));
                startIdx = parseParams(type, minIdx, params, required) + 1;
            }
        }
        return startIdx;
    }


    private String tryImport0(String param, boolean required) {
        var prefix = "";
        var qualifiedName = param;
        if (param.startsWith("? extends ")) {
            prefix = "? extends ";
            qualifiedName = param.substring(prefix.length());
        }

        var idx = qualifiedName.lastIndexOf('.');
        var pkg = idx == -1 ? "" : qualifiedName.substring(0, idx);
        var cls = qualifiedName.substring(idx + 1);
        var imp = imports.get(cls);
        if (imp == null) {
            imp = new ImportLine();
            imp.className = cls;
            imp.packageName = pkg;
            imports.put(cls, imp);
            return prefix + cls;
        } else if (pkg.equals(imp.packageName)) {
            return prefix + cls;
        } else if (required) {
            throw new IllegalArgumentException(param + " cannot be imported: conflict");
        } else {
            return prefix + qualifiedName;
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

    private static class TypePart {
        final String name;
        final List<TypePart> params;

        TypePart(String name) {
            this(name, List.of());
        }

        TypePart(String name, List<TypePart> params) {
            this.name = name;
            this.params = params;
        }

        @Override
        public String toString() {
            var result = new StringBuilder();
            toString(result);
            return result.toString();
        }

        private void toString(StringBuilder result) {
            result.append(name);
            if (!params.isEmpty()) {
                result.append('<');
                var i = params.iterator();
                while (i.hasNext()) {
                    i.next().toString(result);
                    if (i.hasNext()) {
                        result.append(", ");
                    }
                }
                result.append('>');
            }
        }
    }
}
