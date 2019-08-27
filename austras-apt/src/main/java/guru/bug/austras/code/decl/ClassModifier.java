package guru.bug.austras.code.decl;

import guru.bug.austras.code.CodeWriter;
import guru.bug.austras.code.Writable;

public enum ClassModifier implements Writable {
    PUBLIC("public"),
    PROTECTED("protected"),
    PRIVATE("private"),
    ABSTRACT("abstract"),
    STATIC("static"),
    FINAL("final"),
    STRICTFP("strictfp");

    private final String modifier;

    ClassModifier(String modifier) {

        this.modifier = modifier;
    }

    @Override
    public void write(CodeWriter out) {
        out.write(modifier);
    }
}
