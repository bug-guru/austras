package guru.bug.austras.convert.json.writer;


import guru.bug.austras.convert.json.utils.JsonWritingException;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

import static java.util.Objects.requireNonNull;

class JsonTokenWriter {
    private final StringBuilder valueBuilder = new StringBuilder();
    private final Writer out;

    JsonTokenWriter(Writer out) {
        this.out = requireNonNull(out);
    }

    void writeRaw(String rawStr) {
        try {
            out.write(rawStr);
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void write(String str) {
        try {
            valueBuilder.setLength(0);
            valueBuilder.append('"');
            str.codePoints().forEach(this::writeChar);
            valueBuilder.append('"');
            out.append(valueBuilder);
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    private void writeChar(int codePoint) {
        switch (codePoint) {
            case '"':
                valueBuilder.append("\\\"");
                break;
            case '\\':
                valueBuilder.append("\\\\");
                break;
            case '\b':
                valueBuilder.append("\\b");
                break;
            case '\n':
                valueBuilder.append("\\n");
                break;
            case '\r':
                valueBuilder.append("\\r");
                break;
            case '\t':
                valueBuilder.append("\\t");
                break;
            default:
                valueBuilder.appendCodePoint(codePoint);
        }
    }

    void write(byte value) {
        try {
            out.append(Integer.toString(value));
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void write(short value) {
        try {
            out.append(Integer.toString(value));
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }

    }

    void write(int value) {
        try {
            out.append(Integer.toString(value));
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }

    }

    void write(long value) {
        try {
            out.append(Long.toString(value));
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }

    }

    void write(double value) {
        try {
            if (Double.isFinite(value)) {
                out.append(Double.toString(value));
            } else {
                out.append("null");
            }
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void write(float value) {
        try {
            if (Float.isFinite(value)) {
                out.append(Float.toString(value));
            } else {
                out.append("null");
            }
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void write(BigInteger value) {
        try {
            if (value == null) {
                out.append("null");
            } else {
                out.append(value.toString());
            }
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void write(BigDecimal value) {
        try {
            if (value == null) {
                out.append("null");
            } else {
                out.append(value.toString());
            }
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void write(boolean value) {
        try {
            out.append(Boolean.toString(value));
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void write(char value) {
        write(String.valueOf(value));
    }

    void writeBeginObject() {
        try {
            out.append("{");
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void writeEndObject() {
        try {
            out.append("}");
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void writeBeginArray() {
        try {
            out.append("[");
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void writeEndArray() {
        try {
            out.append("]");
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void writeColon() {
        try {
            out.append(":");
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void writeComma() {
        try {
            out.append(",");
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void writeNull() {
        try {
            out.append("null");
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

}
