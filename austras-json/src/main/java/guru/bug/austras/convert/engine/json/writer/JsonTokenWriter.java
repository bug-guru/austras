package guru.bug.austras.convert.engine.json.writer;


import guru.bug.austras.convert.engine.json.utils.JsonWritingException;

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

    /* BOOLEAN */

    void writeBoolean(boolean value) {
        try {
            out.append(Boolean.toString(value));
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void writeBoolean(Boolean value) {
        try {
            if (value == null) {
                out.append("null");
            } else {
                out.append(Boolean.toString(value));
            }
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    /* BYTE */

    void writeByte(byte value) {
        try {
            out.append(Integer.toString(value));
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void writeByte(Byte value) {
        try {
            if (value == null) {
                out.append("null");
            } else {
                out.append(Integer.toString(value));
            }
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    /* SHORT */

    void writeShort(short value) {
        try {
            out.append(Integer.toString(value));
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void writeShort(Short value) {
        try {
            if (value == null) {
                out.append("null");
            } else {
                out.append(Integer.toString(value));
            }
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    /* INTEGER */

    void writeInteger(int value) {
        try {
            out.append(Integer.toString(value));
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void writeInteger(Integer value) {
        try {
            if (value == null) {
                out.append("null");
            } else {
                out.append(Integer.toString(value));
            }
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    /* LONG */

    void writeLong(long value) {
        try {
            out.append(Long.toString(value));
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void writeLong(Long value) {
        try {
            if (value == null) {
                out.append("null");
            } else {
                out.append(Long.toString(value));
            }
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    /* FLOAT */

    void writeFloat(float value) {
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

    void writeFloat(Float value) {
        try {
            if (value == null || !Float.isFinite(value)) {
                out.append("null");
            } else {
                out.append(Float.toString(value));
            }
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    /* DOUBLE */

    void writeDouble(double value) {
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

    void writeDouble(Double value) {
        try {
            if (value == null || !Double.isFinite(value)) {
                out.append("null");
            } else {
                out.append(Double.toString(value));
            }
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    /* BIG INTEGER */

    void writeBigInteger(BigInteger value) {
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

    /* BIG DECIMAL */

    void writeBigDecimal(BigDecimal value) {
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

    /* STRING */

    void writeString(String str) {
        try {
            if (str == null) {
                out.append("null");
            } else {
                valueBuilder.setLength(0);
                valueBuilder.append('"');
                str.codePoints().forEach(this::writeCharacter);
                valueBuilder.append('"');
                out.append(valueBuilder);
            }
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    /* CHARACTER */

    private void writeCharacter(int codePoint) {
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

    void writeCharacter(char value) {
        writeString(String.valueOf(value));
    }

    void writeCharacter(Character value) {
        try {
            if (value == null) {
                out.append("null");
            } else {
                writeString(String.valueOf(value));
            }
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    /* OTHER */

    void writeNull() {
        try {
            out.append("null");
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
    }

    void writeRaw(String rawStr) {
        try {
            out.write(rawStr);
        } catch (IOException e) {
            throw new JsonWritingException(e);
        }
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

}
