package guru.bug.austras.codegen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@FromTemplate("/guru/bug/austras/codetempl/test/loop_and_var.txt")
public class GeneratorTest extends Generator {
    private int externalVar;
    private int internalVar;

    public GeneratorTest() throws IOException {
        super();
    }

    @Test
    public void generateTest() throws IOException {
        var out = new StringWriter(1024);
        this.generateTo(out);
        var result = out.toString();
        Assertions.assertEquals("This is first line\n" +
                "0.100\n" +
                "0.101\n" +
                "0.102\n" +
                "1.100\n" +
                "1.101\n" +
                "1.102\n" +
                "2.100\n" +
                "2.101\n" +
                "2.102\n" +
                "This is last line\n", result);
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
