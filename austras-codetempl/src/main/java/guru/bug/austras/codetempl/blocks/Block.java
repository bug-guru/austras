package guru.bug.austras.codetempl.blocks;

import guru.bug.austras.codetempl.Context;
import guru.bug.austras.codetempl.Printable;

import java.util.List;

public interface Block {
    List<Printable> evaluate(Context ctx);
}
