package guru.bug.fuzzbagel.test;

public abstract class TestComponentDAbstract implements TestComponentD {

  private final TestComponentA a;

  public TestComponentDAbstract(TestComponentA a) {
    this.a = a;
  }
}
