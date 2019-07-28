package guru.bug.fuzzbagel.apt.core;

import javax.lang.model.element.ExecutableElement;
import java.util.HashSet;
import java.util.Set;

public class FuzzBagel {
  private static Set<FuzzBagel> instances = new HashSet<>();

  private FuzzBagel() {
  }

  public static FuzzBagel newInstance() {
    var bagel = new FuzzBagel();
    instances.add(bagel);
    return bagel;
  }

  private static void allDone() {
  }

  public void done() {
    if (!instances.remove(this)) {
      throw new IllegalStateException("Already removed" );
    }
    if (instances.isEmpty()) {
      FuzzBagel.allDone();
    }
  }

  public void getCallingProvider(ExecutableElement method) {

  }
}
