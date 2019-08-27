package guru.bug.austras.code;


public class TypeName implements Writable {
    private final PackageSpec packageSpec;
    private final String simpleName;

    private TypeName(PackageSpec packageSpec, String simpleName) {
        this.packageSpec = packageSpec;
        this.simpleName = simpleName;
    }

    public static TypeName of(String packageName, String simpleName) {
        return new TypeName(PackageSpec.of(packageName), simpleName);
    }

    public static TypeName of(PackageSpec packageSpec, String simpleName) {
        return new TypeName(packageSpec, simpleName);
    }

    public static TypeName of(String qualifiedName) {
        var index = qualifiedName.lastIndexOf('.');
        if (index == -1) {
            return new TypeName(PackageSpec.empty(), qualifiedName);
        } else {
            String pkg = qualifiedName.substring(0, index);
            String cls = qualifiedName.substring(index + 1);
            return new TypeName(PackageSpec.of(pkg), cls);
        }
    }

    public PackageSpec getPackageSpec() {
        return packageSpec;
    }

    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public void write(CodeWriter out) {
        if (!out.checkImported(this)) {
            out.write(packageSpec);
            if (!packageSpec.isBlank()) {
                out.write(".");
            }
        }
        out.write(simpleName);
    }
}
