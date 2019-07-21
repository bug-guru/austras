package guru.bug.fuzzbagel.test;

public class TestComponentCImpl implements TestComponentC {

  private final TestComponentA a;
  private final TestComponentB b;

  public TestComponentCImpl(TestComponentA a, TestComponentB b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public void doSomething() {}
}
