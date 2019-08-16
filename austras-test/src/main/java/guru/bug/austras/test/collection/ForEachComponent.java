package guru.bug.austras.test.collection;

import guru.bug.austras.annotations.Component;

import java.util.Collection;

@Component
public class ForEachComponent {
    private final Collection<CompItem> items;

    public ForEachComponent(Collection<CompItem> items) {
        this.items = items;
    }
}
