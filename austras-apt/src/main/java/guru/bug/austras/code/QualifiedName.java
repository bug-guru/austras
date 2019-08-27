package guru.bug.austras.code;


import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QualifiedName implements Writable {
    private final PackageName packageName;
    private final SimpleName simpleName;

    private QualifiedName(PackageName packageName, SimpleName simpleName) {
        this.packageName = packageName;
        this.simpleName = simpleName;
    }

    public static QualifiedName of(String packageName, String simpleName) {
        return new QualifiedName(PackageName.of(packageName), SimpleName.of(simpleName));
    }

    public static QualifiedName of(PackageName packageName, SimpleName simpleName) {
        return new QualifiedName(packageName, simpleName);
    }

    public static QualifiedName of(Class<?> clazz) {
        return of(clazz.getPackageName(), clazz.getSimpleName());
    }

    public static QualifiedName of(String qualifiedName) {
        var segments = qualifiedName.split("\\.");
        List<String> pkgSegments = Stream.of(segments)
                .takeWhile(QualifiedName::startsWithLower)
                .collect(Collectors.toList());
        List<String> typeSegments = Stream.of(segments)
                .dropWhile(QualifiedName::startsWithLower)
                .collect(Collectors.toList());
        PackageName packageName;
        if (pkgSegments.isEmpty()) {
            packageName = PackageName.root();
        } else {
            packageName = PackageName.of(StringUtils.join(pkgSegments, '.'));
        }
        SimpleName simpleName = SimpleName.of(StringUtils.join(typeSegments, '.'));
        return new QualifiedName(packageName, simpleName);
    }

    private static boolean startsWithLower(String str) {
        int first = str.codePointAt(0);
        return Character.isLowerCase(first);
    }

    public PackageName getPackageName() {
        return packageName;
    }

    public SimpleName getSimpleName() {
        return simpleName;
    }

    @Override
    public void write(CodeWriter out) {
        if (!packageName.isRoot() && !out.checkImported(this)) {
            out.write(packageName);
            out.write(".");
        }
        out.write(simpleName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QualifiedName that = (QualifiedName) o;
        return Objects.equals(packageName, that.packageName) &&
                Objects.equals(simpleName, that.simpleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageName, simpleName);
    }
}
