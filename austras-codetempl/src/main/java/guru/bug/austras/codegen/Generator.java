package guru.bug.austras.codegen;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Generator {
    private final BlockWithBody content;
    private final HashMap<String, Caller> callers = new HashMap<>();

    protected Generator() throws IOException {
        collectCallers();
        this.content = createContentBlock();
    }

    public void generateTo(OutputStream out) throws IOException {
        try (var writer = new OutputStreamWriter(out)) {
            generateTo(writer);
        }
    }

    public String generateToString() {
        return content.evaluateBody();
    }

    public void generateTo(Writer writer) throws IOException {
        String outputContent = content.evaluateBody();
        writer.write(outputContent);
    }

    private BlockWithBody createContentBlock() throws IOException {
        var fromTemplate = getClass().getAnnotation(FromTemplate.class);
        var resourceName = fromTemplate.value();
        var strContent = readContent(resourceName);
        TemplateTokenizer tokenizer = new TemplateTokenizer();
        var tokens = tokenizer.process(strContent);
        cleanup(tokens);

        var tokenIterator = tokens.iterator();
        return new BlockWithBody(null, tokenIterator);
    }

    private void cleanup(List<TemplateToken> tokens) {
        var i = tokens.listIterator();
        int state = 1;
        boolean hasTrailingSpaces = false;
        boolean hasLeadingSpaces = false;

        while (i.hasNext()) {
            var t = i.next();
            if ((state == 0 || state == 1) && t.getType() == TemplateToken.Type.NEW_LINE) {
                state = 1;
                continue;
            } else if (state == 1 && t.getType() == TemplateToken.Type.TEXT && t.getValue().isBlank()) {
                state = 2;
                hasLeadingSpaces = true;
                continue;
            } else if (state == 1 && t.getType() == TemplateToken.Type.BLOCK) {
                state = 3;
                continue;
            } else if (state == 2 && t.getType() == TemplateToken.Type.BLOCK) {
                state = 3;
                continue;
            } else if (state == 3 && t.getType() == TemplateToken.Type.TEXT && t.getValue().isBlank()) {
                state = 4;
                hasTrailingSpaces = true;
                continue;
            } else if ((state == 3 || state == 4) && t.getType() == TemplateToken.Type.NEW_LINE) {
                i.remove();
                if (hasTrailingSpaces) {
                    i.previous();
                    i.remove();
                }
                if (hasLeadingSpaces) {
                    i.previous();
                    i.previous();
                    i.remove();
                    i.next();
                }
            }
            state = 0;
            hasLeadingSpaces = false;
            hasTrailingSpaces = false;
        }
    }

    private void collectCallers() {
        for (var m : getClass().getMethods()) {
            var ft = m.getAnnotation(FromTemplate.class);
            if (ft == null) {
                continue;
            }
            this.callers.put(ft.value(), new Caller(m));
        }
    }

    private String readContent(String resourceName) throws IOException {
        try (var is = getClass().getResourceAsStream(resourceName);
             var reader = new InputStreamReader(is)) {
            var writer = new StringWriter(2048);
            reader.transferTo(writer);
            return writer.toString();
        }
    }

    private Caller findCaller(TemplateToken t) {
        var result = callers.get(t.getValue());
        if (result == null) {
            throw new IllegalArgumentException("Caller for template " + t + " not found");
        }
        return result;
    }

    private enum CallerType {
        BODYBLOCK_PRINTWRITER_NO_RETURN((method, generator, out, block) -> method.invoke(generator, block, out)),
        STRING_PRINTWRITER_NO_RETURN((method, generator, out, block) -> method.invoke(generator, block.evaluateBody(), out)),
        PRINTWRITER_BODYBLOCK_NO_RETURN((method, generator, out, block) -> method.invoke(generator, out, block)),
        PRINTWRITER_STRING_NO_RETURN((method, generator, out, block) -> method.invoke(generator, out, block.evaluateBody())),
        PRINTWRITER_NO_RETURN((method, generator, out, block) -> method.invoke(generator, out)),
        NOTHING_WITH_RETURN((method, generator, out, block) -> out.print(method.invoke(generator))),
        BODYBLOCK_WITH_RETURN((method, generator, out, block) -> out.print(method.invoke(generator, block))),
        STRING_WITH_RETURN((method, generator, out, block) -> out.print(method.invoke(generator, block.evaluateBody())));

        private final Invocation invocation;

        CallerType(Invocation invocation) {
            this.invocation = invocation;
        }

        static CallerType find(Method method) {
            try {
                var name = new StringBuilder(50);

                if (method.getParameterCount() == 0) {
                    name.append("NOTHING");
                } else {
                    name.append(Stream.of(method.getParameters())
                            .map(p -> p.getType().getSimpleName().toUpperCase())
                            .collect(Collectors.joining("_")));
                }

                if (method.getReturnType().equals(Void.TYPE)) {
                    name.append("_NO_RETURN");
                } else {
                    name.append("_WITH_RETURN");
                }
                return valueOf(name.toString());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Illegal template method: " + method, e);
            }
        }
    }

    private interface Invocation {
        void invoke(Method method, Generator generator, PrintWriter out, BodyBlock block) throws InvocationTargetException, IllegalAccessException;
    }

    private interface Block {
        void writeTo(PrintWriter out);
    }

    private class TextBlock implements Block {
        private final String text;

        private TextBlock(String text) {
            this.text = text;
        }

        @Override
        public void writeTo(PrintWriter out) {
            out.print(text);
        }
    }

    private class NewLineBlock implements Block {

        @Override
        public void writeTo(PrintWriter out) {
            out.println();
        }
    }

    private class ValueBlock implements Block {
        private final Caller caller;

        private ValueBlock(Caller caller) {
            this.caller = caller;
        }

        @Override
        public void writeTo(PrintWriter out) {
            caller.call(out, null);
        }
    }

    private class BlockWithBody implements Block, BodyBlock {
        private final Caller caller;
        private final List<Block> blocks;

        private BlockWithBody(Caller caller, Iterator<TemplateToken> tokenIterator) {
            this.caller = caller;
            this.blocks = new ArrayList<Block>();
            loop:
            while (tokenIterator.hasNext()) {
                Block block;
                var t = tokenIterator.next();
                switch (t.getType()) {
                    case NEW_LINE:
                        block = new NewLineBlock();
                        break;
                    case TEXT:
                        block = new TextBlock(t.getValue());
                        break;
                    case VALUE:
                        block = new ValueBlock(findCaller(t));
                        break;
                    case BLOCK:
                        if ("END".equals(t.getValue())) {
                            break loop;
                        }
                        block = new BlockWithBody(findCaller(t), tokenIterator);
                        break;
                    default:
                        throw new IllegalStateException("Unsupported token " + t.getType());
                }
                this.blocks.add(block);
            }
        }

        @Override
        public void writeTo(PrintWriter out) {
            caller.call(out, this);
        }

        @Override
        public String evaluateBody() {
            var result = new StringWriter(2048);
            var out = new PrintWriter(result);
            for (var b : blocks) {
                b.writeTo(out);
            }
            return result.toString();
        }
    }

    private class Caller {
        private final CallerType callerType;
        private final Method method;

        private Caller(Method method) {
            this.method = method;
            callerType = CallerType.find(method);
        }

        private void call(PrintWriter out, BodyBlock block) {
            try {
                callerType.invocation.invoke(method, Generator.this, out, block);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
