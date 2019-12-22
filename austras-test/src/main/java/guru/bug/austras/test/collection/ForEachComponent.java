package guru.bug.austras.test.collection;

import guru.bug.austras.core.Component;
import guru.bug.austras.core.Selector;

@Component
@SuppressWarnings("ALL") // this class is for testing only
public class ForEachComponent {
    private final Selector<? extends CompItem> items; //NOSONAR for testing purposes only

    public ForEachComponent(Selector<? extends CompItem> items) {
        this.items = items;
    }
}
