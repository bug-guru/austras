package guru.bug.austras.code;


public class TypeName implements Writable {
    private final String qualifiedName;
    private final String packageName;
    private final String simpleName;

    private TypeName(String qualifiedName, String packageName, String simpleName) {
        this.qualifiedName = qualifiedName;
        this.packageName = packageName;
        this.simpleName = simpleName;
    }

    public static TypeName of(String packageName, String simpleName) {
        return new TypeName(packageName + "." + simpleName, packageName, simpleName);
    }

    public static TypeName of(String qualifiedName) {
        var index = qualifiedName.lastIndexOf('.');
        if (index == -1) {
            return new TypeName(qualifiedName, null, qualifiedName);
        } else {
            String pkg = qualifiedName.substring(0, index);
            String cls = qualifiedName.substring(index + 1);
            return new TypeName(qualifiedName, pkg, cls);
        }
    }

    @Override
    public String toString() {
        return qualifiedName;
    }
}
