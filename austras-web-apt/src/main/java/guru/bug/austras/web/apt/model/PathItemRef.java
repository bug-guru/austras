package guru.bug.austras.web.apt.model;

public class PathItemRef {
    private final String type;
    private final String value;

    public PathItemRef(String item) {
        if (item.startsWith("{") && item.endsWith("}")) {
            type = "param";
            value = item.substring(1, item.length() - 1);
        } else {
            type = "matching";
            value = item;
        }
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
