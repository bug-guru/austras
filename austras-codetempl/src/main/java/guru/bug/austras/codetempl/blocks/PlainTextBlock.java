package guru.bug.austras.codetempl.blocks;

import guru.bug.austras.codetempl.Context;
import guru.bug.austras.codetempl.Printable;

import java.util.List;

public class PlainTextBlock implements Block {
    private final String text;

    private PlainTextBlock(String text) {
        this.text = text;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public List<Printable> evaluate(Context ctx) {
        return List.of(out -> out.write(text));
    }

    public static class Builder {
        private final StringBuilder text = new StringBuilder();

        public Builder append(String str) {
            text.append(str);
            return this;
        }

        public Builder append(StringBuilder sb) {
            text.append(sb);
            return this;
        }

        public Builder append(CharSequence s) {
            text.append(s);
            return this;
        }

        public Builder append(CharSequence s, int start, int end) {
            text.append(s, start, end);
            return this;
        }

        public Builder append(char[] str) {
            text.append(str);
            return this;
        }

        public Builder append(char[] str, int offset, int len) {
            text.append(str, offset, len);
            return this;
        }

        public Builder append(boolean b) {
            text.append(b);
            return this;
        }

        public Builder append(char c) {
            text.append(c);
            return this;
        }

        public Builder append(int i) {
            text.append(i);
            return this;
        }

        public Builder append(long lng) {
            text.append(lng);
            return this;
        }

        public Builder append(float f) {
            text.append(f);
            return this;
        }

        public Builder append(double d) {
            text.append(d);
            return this;
        }

        public Builder appendCodePoint(int codePoint) {
            text.appendCodePoint(codePoint);
            return this;
        }

        public PlainTextBlock build() {
            return new PlainTextBlock(text.toString());
        }
    }
}
