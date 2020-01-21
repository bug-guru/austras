/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import guru.bug.austras.codegen.template.TemplateException;
import guru.bug.austras.codegen.template.parser.TemplateCleaner;
import guru.bug.austras.codegen.template.parser.TemplateToken;
import guru.bug.austras.codegen.template.parser.TemplateTokenizer;

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

    @SuppressWarnings("WeakerAccess")
    protected Generator() throws IOException, TemplateException {
        collectCallers();
        this.content = createContentBlock();
    }

    public void generateTo(OutputStream out) throws IOException {
        try (var writer = new OutputStreamWriter(out)) {
            generateTo(writer);
        }
    }

    @SuppressWarnings("WeakerAccess")
    protected String generateToString() {
        return content.evaluateBody();
    }

    @SuppressWarnings("WeakerAccess")
    protected void generateTo(Writer writer) throws IOException {
        String outputContent = generateToString();
        writer.write(outputContent);
    }

    private BlockWithBody createContentBlock() throws IOException, TemplateException {
        var fromTemplate = getClass().getAnnotation(Template.class);
        if (fromTemplate == null) {
            throw new TemplateException("Annotation @Template not found on class " + getClass().getName());
        }
        var resourceName = fromTemplate.name();
        var strContent = readContent(resourceName);
        TemplateTokenizer tokenizer = new TemplateTokenizer();
        var tokens = tokenizer.process(strContent);
        TemplateCleaner.cleanup(tokens);

        var tokenIterator = tokens.iterator();
        return new BlockWithBody(null, tokenIterator);
    }

    private void collectCallers() {
        for (var m : getClass().getMethods()) {
            var ft = m.getAnnotation(Template.class);
            if (ft == null) {
                continue;
            }
            this.callers.put(ft.name(), new Caller(m));
        }
    }

    private String readContent(String resourceName) throws IOException, TemplateException {
        try (var is = getClass().getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new TemplateException(resourceName + " not found for " + getClass().getName());
            }
            try (var reader = new InputStreamReader(is)) {
                var writer = new StringWriter(2048);
                reader.transferTo(writer);
                return writer.toString();
            }
        }
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

    private static class TextBlock implements Block {
        private final String text;

        private TextBlock(String text) {
            this.text = text;
        }

        @Override
        public void writeTo(PrintWriter out) {
            out.print(text);
        }
    }

    private static class NewLineBlock implements Block {

        @Override
        public void writeTo(PrintWriter out) {
            out.println();
        }
    }

    private static class ValueBlock implements Block {
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
            this.blocks = collectBlocks(tokenIterator);
        }

        private List<Block> collectBlocks(Iterator<TemplateToken> tokenIterator) {
            var result = new ArrayList<Block>();
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
                            result.trimToSize();
                            return result;
                        }
                        block = new BlockWithBody(findCaller(t), tokenIterator);
                        break;
                    default:
                        throw new IllegalStateException("Unsupported token " + t.getType());
                }
                result.add(block);
            }
            result.trimToSize();
            return result;
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

        private Caller findCaller(TemplateToken t) {
            var result = callers.get(t.getValue());
            if (result == null) {
                throw new IllegalArgumentException("Caller for template " + t + " not found");
            }
            return result;
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
