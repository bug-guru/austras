package guru.bug.austras.codetempl;

import guru.bug.austras.codetempl.blocks.ExpressionBlock;
import guru.bug.austras.codetempl.blocks.LoopBlock;
import guru.bug.austras.codetempl.blocks.PlainTextBlock;
import guru.bug.austras.codetempl.expressions.ConcatenateExpression;
import guru.bug.austras.codetempl.expressions.LiteralExpression;
import guru.bug.austras.codetempl.expressions.TernaryExpression;
import guru.bug.austras.codetempl.expressions.VarExpression;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateTest {


    private String execute(Template template) throws Exception {
        return execute(template, b -> {

        });
    }

    private String execute(Template template, Consumer<Context.Builder> contextBuilder) throws Exception {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        var ctx = Context.builder();
        contextBuilder.accept(ctx);
        template.execute(ctx.build(), pw);
        pw.close();
        sw.close();
        return sw.toString();
    }

    @Test
    void plainTextBlockTest() throws Exception {
        var result = execute(Template.builder()
                .add(PlainTextBlock.builder()
                        .append("Start")
                        .append(10)
                        .append('x')
                        .build())
                .add(PlainTextBlock.builder()
                        .append('y')
                        .append(20)
                        .append("Finish")
                        .build())
                .build());
        assertEquals("Start10xy20Finish", result);
    }

    @Test
    void expressionBlock_LiteralExpression_Test() throws Exception {
        var result = execute(Template.builder()
                .add(ExpressionBlock.of(LiteralExpression.of("Start")))
                .add(ExpressionBlock.of(LiteralExpression.of(100)))
                .add(ExpressionBlock.of(LiteralExpression.of(true)))
                .add(ExpressionBlock.of(LiteralExpression.of(false)))
                .add(ExpressionBlock.of(LiteralExpression.of("Finish")))
                .build());
        assertEquals("Start100truefalseFinish", result);
    }

    @Test
    void expressionBlock_VarExpression_Test() throws Exception {
        var result = execute(Template.builder()
                        .add(ExpressionBlock.of(LiteralExpression.of("Start")))
                        .add(ExpressionBlock.of(VarExpression.of("VAR")))
                        .add(ExpressionBlock.of(LiteralExpression.of("Finish")))
                        .build(),
                ctx -> ctx.put("VAR", Value.of("HELLO!")));
        assertEquals("StartHELLO!Finish", result);
    }

    @Test
    void loopBlockTest() throws Exception {
        var result = execute(Template.builder()
                        .add(PlainTextBlock.builder().append("Start").build())
                        .add(LoopBlock.builder()
                                .collectionVar("COLLECTION")
                                .loopVar("C")
                                .addBlock(ExpressionBlock.of(TernaryExpression.builder()
                                        .booleanExpression(VarExpression.of("C.IS_FIRST"))
                                        .trueExpression(LiteralExpression.of(">>"))
                                        .build()))
                                .addBlock(PlainTextBlock.builder().append("Loop").build())
                                .addBlock(ExpressionBlock.of(ConcatenateExpression.of(
                                        VarExpression.of("C.VALUE"),
                                        LiteralExpression.of("("),
                                        VarExpression.of("C.INDEX"),
                                        LiteralExpression.of(")"))))
                                .addBlock(ExpressionBlock.of(TernaryExpression.builder()
                                        .booleanExpression(VarExpression.of("C.IS_LAST"))
                                        .trueExpression(LiteralExpression.of("<<"))
                                        .falseExpression(LiteralExpression.of(", "))
                                        .build()))
                                .build())
                        .add(PlainTextBlock.builder().append("Finish").build())
                        .build(),
                ctx -> ctx.put("COLLECTION", Value.builder()
                        .array(a -> a.add(Value.of("A"))
                                .add(Value.of("B"))
                                .add(Value.of("C")))
                        .build()));
        assertEquals("Start>>LoopA(0), LoopB(1), LoopC(2)<<Finish", result);
    }
}