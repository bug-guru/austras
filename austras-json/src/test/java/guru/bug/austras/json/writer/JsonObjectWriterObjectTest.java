/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.writer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonObjectWriterObjectTest extends JsonObjectWriterAbstractTest {

    private static final JsonObjectSerializer<TheBean> theBeanSerializer = (v, w) -> {
        w.writeString("type", v.type);
        w.writeString("value", v.value);
        w.writeInteger("order", v.orderNr);
    };

    @Test
    void writeObject() {
        var bean = new TheBean("magic", "mirror", 1);
        ow.writeObject("key", bean, theBeanSerializer);
        assertEquals(p("key", bean.toString()), out.toString());
    }

    @Test
    void writeObjectNull() {
        ow.writeObject("key", null, theBeanSerializer);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectArrayArray() {
        var bean1 = new TheBean("magic", "mirror", 1);
        var bean2 = new TheBean("cat", "mew", 2);
        var bean3 = new TheBean("dog", "bark", 3);
        ow.writeObjectArray("key", new TheBean[]{bean1, bean2, null, bean3}, theBeanSerializer);
        assertEquals(p("key", "[" + bean1 + "," + bean2 + ",null," + bean3 + "]"), out.toString());
    }

    @Test
    void writeObjectArrayArrayEmpty() {
        ow.writeObjectArray("key", new TheBean[]{}, theBeanSerializer);
        assertEquals(p("key", "[]"), out.toString());
    }

    @Test
    void writeObjectArrayArrayNull() {
        ow.writeObjectArray("key", (TheBean[]) null, theBeanSerializer);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectArrayCollection() {
        var bean1 = new TheBean("magic", "mirror", 1);
        var bean2 = new TheBean("cat", "mew", 2);
        var bean3 = new TheBean("dog", "bark", 3);
        ow.writeObjectArray("key", Arrays.asList(null, bean1, bean2, bean3), theBeanSerializer);
        assertEquals(p("key", "[null," + bean1 + "," + bean2 + "," + bean3 + "]"), out.toString());
    }

    @Test
    void writeObjectArrayCollectionEmpty() {
        ow.writeObjectArray("key", List.of(), theBeanSerializer);
        assertEquals(p("key", "[]"), out.toString());
    }

    @Test
    void writeObjectArrayCollectionNull() {
        ow.writeObjectArray("key", (Collection<TheBean>) null, theBeanSerializer);
        assertEquals(p("key", "null"), out.toString());
    }


    private static class TheBean {
        final String type;
        final String value;
        final int orderNr;

        private TheBean(String type, String value, int orderNr) {
            this.type = type;
            this.value = value;
            this.orderNr = orderNr;
        }

        @Override
        public String toString() {
            return String.format("{\"type\":\"%s\",\"value\":\"%s\",\"order\":%d}", type, value, orderNr);
        }
    }
}
