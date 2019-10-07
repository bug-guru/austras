package guru.bug.austras.codetempl.blocks;

import guru.bug.austras.codetempl.Context;
import guru.bug.austras.codetempl.Printable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ImportsBlock implements Block {
    private final List<Block> children;

    public ImportsBlock(List<Block> children) {
        this.children = children;
    }

    private static Set<Type> parseType(String typeSpec) {

    }

    @Override
    public List<Printable> evaluate(Context ctx) {
        var result = new ArrayList<Printable>();
        for (Block child : children) {
            var lst = child.evaluate(ctx);
            result.addAll(lst);
        }
        return List.of(new ImportsPrintable(result));
    }

    private static class ImportsPrintable implements Printable {
        private final List<Printable> children;
        private final Map<String> importLines = new ArrayList<>();

        private ImportsPrintable(List<Printable> children) {
            this.children = children;
        }

        @Override
        public void print(PrintWriter out) {
            processBody();
        }

        private void processBody() {
            var tmpStr = new StringWriter();
            var tmpPrn = new PrintWriter(tmpStr);
            for (Printable child : children) {
                child.print(tmpPrn);
            }
            tmpPrn.close();
            processBodyLines(tmpStr.toString());
        }

        private void processBodyLines(String body) {
            try (var tmpStr = new StringReader(body);
                 var buf = new BufferedReader(tmpStr)) {
                String str;
                while ((str = buf.readLine()) != null) {
                    registerImport(str.strip());
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        private void registerImport(String importLine) {

        }
    }

    private static class Type {
        private final String qualifiedName;
        private final String packageName;
        private final String simpleName;

        private Type(String qualifiedName, String packageName, String simpleName) {
            this.qualifiedName = qualifiedName;
            this.packageName = packageName;
            this.simpleName = simpleName;
        }
    }
}
