package guru.bug.austras.convert.json.writer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectWriterBooleanTest extends JsonObjectWriterAbstractTest {
    private static final JsonBooleanSerializer CUSTOM_PRIMITIVE_BOOLEAN_SERIALIZER = (v, w) -> w.writeInteger(v ? 1 : 0);
    private static final JsonSerializer<Boolean> CUSTOM_OBJECT_BOOLEAN_SERIALIZER = (v, w) -> w.writeInteger(v == null ? -1 : (v ? 1 : 0));

    @Test
    void writePrimitiveBooleanTrueWithConverter() {
        ow.writeBoolean("key" , true, CUSTOM_PRIMITIVE_BOOLEAN_SERIALIZER);
        assertEquals(p("key" , "1"), out.toString());
    }

    @Test
    void writePrimitiveBooleanFalseWithConverter() {
        ow.writeBoolean("key" , false, CUSTOM_PRIMITIVE_BOOLEAN_SERIALIZER);
        assertEquals(p("key" , "0"), out.toString());
    }

    @Test
    void writeObjectBooleanTrueWithConverter() {
        ow.writeBoolean("key" , Boolean.TRUE, CUSTOM_OBJECT_BOOLEAN_SERIALIZER);
        assertEquals(p("key" , "1"), out.toString());
    }

    @Test
    void writeObjectBooleanFalseWithConverter() {
        ow.writeBoolean("key" , Boolean.FALSE, CUSTOM_OBJECT_BOOLEAN_SERIALIZER);
        assertEquals(p("key" , "0"), out.toString());
    }

    @Test
    void writeObjectBooleanNullWithConverter() {
        ow.writeBoolean("key" , null, CUSTOM_OBJECT_BOOLEAN_SERIALIZER);
        assertEquals(p("key" , "-1"), out.toString());
    }

    @Test
    void writePrimitiveBooleanArrayWithConverter() {
        ow.writeBooleanArray("key" , new boolean[]{true, true, false, false}, CUSTOM_PRIMITIVE_BOOLEAN_SERIALIZER);
        assertEquals(p("key" , "[1,1,0,0]"), out.toString());
    }

    @Test
    void writePrimitiveBooleanArrayNullWithConverter() {
        ow.writeBooleanArray("key" , null, CUSTOM_PRIMITIVE_BOOLEAN_SERIALIZER);
        assertEquals(p("key" , "null"), out.toString());
    }

    @Test
    void writeObjectBooleanArrayWithConverter() {
        ow.writeBooleanArray("key" , new Boolean[]{Boolean.FALSE, Boolean.TRUE, null}, CUSTOM_OBJECT_BOOLEAN_SERIALIZER);
        assertEquals(p("key" , "[0,1,-1]"), out.toString());
    }

    @Test
    void writeObjectBooleanArrayNullWithConverter() {
        ow.writeBooleanArray("key" , (Boolean[]) null, CUSTOM_OBJECT_BOOLEAN_SERIALIZER);
        assertEquals(p("key" , "null"), out.toString());
    }

    @Test
    void writeObjectBooleanCollectionWithConverter() {
        ow.writeBooleanArray("key" , Arrays.asList(true, false, null), CUSTOM_OBJECT_BOOLEAN_SERIALIZER);
        assertEquals(p("key" , "[1,0,-1]"), out.toString());
    }

    @Test
    void writeObjectBooleanCollectionNullWithConverter() {
        ow.writeBooleanArray("key" , (Collection<Boolean>) null, CUSTOM_OBJECT_BOOLEAN_SERIALIZER);
        assertEquals(p("key" , "null"), out.toString());
    }

    @Test
    void writePrimitiveBooleanTrue() {
        ow.writeBoolean("key" , true);
        assertEquals(p("key" , "true"), out.toString());
    }

    @Test
    void writePrimitiveBooleanFalse() {
        ow.writeBoolean("key" , false);
        assertEquals(p("key" , "false"), out.toString());
    }

    @Test
    void writeObjectBooleanTrue() {
        ow.writeBoolean("key" , Boolean.TRUE);
        assertEquals(p("key" , "true"), out.toString());
    }

    @Test
    void writeObjectBooleanFalse() {
        ow.writeBoolean("key" , Boolean.FALSE);
        assertEquals(p("key" , "false"), out.toString());
    }

    @Test
    void writeObjectBooleanNull() {
        ow.writeBoolean("key" , null);
        assertEquals(p("key" , "null"), out.toString());
    }

    @Test
    void writePrimitiveBooleanArray() {
        ow.writeBooleanArray("key" , new boolean[]{true, true, false, false});
        assertEquals(p("key" , "[true,true,false,false]"), out.toString());
    }

    @Test
    void writePrimitiveBooleanArrayNull() {
        ow.writeBooleanArray("key" , (boolean[]) null);
        assertEquals(p("key" , "null"), out.toString());
    }

    @Test
    void writeObjectBooleanArray() {
        ow.writeBooleanArray("key" , new Boolean[]{Boolean.FALSE, Boolean.TRUE, null});
        assertEquals(p("key" , "[false,true,null]"), out.toString());
    }

    @Test
    void writeObjectBooleanArrayNull() {
        ow.writeBooleanArray("key" , (Boolean[]) null);
        assertEquals(p("key" , "null"), out.toString());
    }

    @Test
    void writeObjectBooleanCollection() {
        ow.writeBooleanArray("key" , Arrays.asList(true, false, null));
        assertEquals(p("key" , "[true,false,null]"), out.toString());
    }

    @Test
    void writeObjectBooleanCollectionNull() {
        ow.writeBooleanArray("key" , (Collection<Boolean>) null);
        assertEquals(p("key" , "null"), out.toString());
    }

}