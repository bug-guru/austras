package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.Template;
import guru.bug.austras.codetempl.parser.tokenizer.TemplateTokenizer;

import java.io.*;

public class TemplateFactory {
    public static Template read(InputStream is) throws IOException {
        try (var reader = new InputStreamReader(is)) {
            return read(reader);
        }
    }

    public static Template read(Reader reader) throws IOException {
        var contentWriter = new StringWriter(2048);
        reader.transferTo(contentWriter);
        var tokenizer = new TemplateTokenizer();
        var tokens = tokenizer.process(contentWriter.toString());
        var parser = new TemplateParser(tokens);
        return parser.parse();
    }

}
