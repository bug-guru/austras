package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.Template;

import java.io.*;

public class TemplateFactory {
    public static Template fromStream(InputStream is) throws IOException {
        try (var reader = new InputStreamReader(is)) {
            return fromReader(reader);
        }
    }

    public static Template fromReader(Reader reader) throws IOException {
        try (var bufReader = new BufferedReader(reader)) {
            return fromBufferedReader(bufReader);
        }
    }

    public static Template fromBufferedReader(BufferedReader reader) throws IOException {
        var tokenizer = new TemplateTokenizer(reader);
        var tokens = tokenizer.tokenize();
        var parser = new TemplateParser(tokens);
        return parser.parse();
    }
}
