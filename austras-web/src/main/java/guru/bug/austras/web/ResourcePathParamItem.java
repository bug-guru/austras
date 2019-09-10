package guru.bug.austras.web;

public class ResourcePathParamItem implements ResourcePathItem {
    @Override
    public boolean canAccept(String value) {
        return true;
    }
}
