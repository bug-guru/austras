package guru.bug.austras.codegen;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;

@FromTemplate("/guru/bug/austras/codetempl/test/loop_and_var.txt")
public class GeneratorTest extends Generator {
    private int externalVar;
    private int internalVar;

    public GeneratorTest() throws IOException {
        super();
    }

    @Test
    public void generateTest() throws IOException {
        this.generateTo(System.out);
    }

    @FromTemplate("EXTERNAL LOOP")
    public void externalLoop(PrintWriter out, BodyBlock body) {
        for (int i = 0; i < 3; i++) {
            externalVar = i;
            out.write(body.evaluateBody());
        }
    }

    @FromTemplate("INTERNAL LOOP")
    public void internalLoop(PrintWriter out, BodyBlock body) {
        for (int i = 100; i < 103; i++) {
            internalVar = i;
            out.write(body.evaluateBody());
        }
    }

    @FromTemplate("INT_VALUE")
    public int getIntVar() {
        return internalVar;
    }

    @FromTemplate("EXT_VALUE")
    public int getExtVar() {
        return externalVar;
    }
}
