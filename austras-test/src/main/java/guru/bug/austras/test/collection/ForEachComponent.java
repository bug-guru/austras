package guru.bug.austras.test.collection;

import guru.bug.austras.core.Component;

import java.util.Collection;

@Component
@SuppressWarnings("ALL") // this class is for testing only
public class ForEachComponent {
    private final Collection<? extends CompItem> items; //NOSONAR for testing purposes only

    public ForEachComponent(Collection<? extends CompItem> items) {
        this.items = items;
    }
}
