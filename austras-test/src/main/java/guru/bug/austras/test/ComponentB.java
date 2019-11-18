package guru.bug.austras.test;

public class ComponentB {
    private final ComponentA componentA; //NOSONAR for testing purposes only

    public ComponentB(ComponentA componentA) {
    this.componentA = componentA;
  }
}
