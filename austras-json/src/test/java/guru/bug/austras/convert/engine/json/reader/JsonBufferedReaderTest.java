package guru.bug.austras.convert.engine.json.reader;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class JsonBufferedReaderTest {

    @Test
    void testNext0() {
        StringReader reader = new StringReader("Hello");
        JsonBufferedReader jsonReader = new JsonBufferedReader(reader);

        assertEquals('H', jsonReader.next0());
        assertEquals('e', jsonReader.next0());
        assertEquals('l', jsonReader.next0());
        assertEquals('l', jsonReader.next0());
        assertEquals('o', jsonReader.next0());
        assertEquals('\u0000', jsonReader.next0());
        assertEquals('\u0000', jsonReader.next0());
        assertEquals('\u0000', jsonReader.next0());
    }

    @Test
    void testNextOneLine() {
        StringReader reader = new StringReader("Hello");
        JsonBufferedReader jsonReader = new JsonBufferedReader(reader);

        assertEquals('H', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(1, jsonReader.getPosition().getCol());
        assertEquals('e', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(2, jsonReader.getPosition().getCol());
        assertEquals('l', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(3, jsonReader.getPosition().getCol());
        assertEquals('l', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(4, jsonReader.getPosition().getCol());
        assertEquals('o', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(5, jsonReader.getPosition().getCol());

        assertEquals('\u0000', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(6, jsonReader.getPosition().getCol());

        assertEquals('\u0000', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(6, jsonReader.getPosition().getCol());
    }

    @Test
    void testNextMultilineLF() {
        checkMultiline("\n");
    }

    @Test
    void testNextMultilineCR() {
        checkMultiline("\r");
    }

    @Test
    void testNextMultilineCRLF() {
        checkMultiline("\r\n");
    }

    @Test
    void testNextMultilineLFCR() {
        checkMultiline("\n\r");
    }

    private void checkMultiline(String newline) {
        StringReader reader = new StringReader("Hello" + newline + "Test" + newline + newline + "World" + newline);
        JsonBufferedReader jsonReader = new JsonBufferedReader(reader);


        assertEquals('H', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(1, jsonReader.getPosition().getCol());
        assertEquals('e', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(2, jsonReader.getPosition().getCol());
        assertEquals('l', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(3, jsonReader.getPosition().getCol());
        assertEquals('l', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(4, jsonReader.getPosition().getCol());
        assertEquals('o', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(5, jsonReader.getPosition().getCol());
        assertEquals('\n', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(6, jsonReader.getPosition().getCol());

        assertEquals('T', jsonReader.next());
        assertEquals(2, jsonReader.getPosition().getRow());
        assertEquals(1, jsonReader.getPosition().getCol());
        assertEquals('e', jsonReader.next());
        assertEquals(2, jsonReader.getPosition().getRow());
        assertEquals(2, jsonReader.getPosition().getCol());
        assertEquals('s', jsonReader.next());
        assertEquals(2, jsonReader.getPosition().getRow());
        assertEquals(3, jsonReader.getPosition().getCol());
        assertEquals('t', jsonReader.next());
        assertEquals(2, jsonReader.getPosition().getRow());
        assertEquals(4, jsonReader.getPosition().getCol());
        assertEquals('\n', jsonReader.next());
        assertEquals(2, jsonReader.getPosition().getRow());
        assertEquals(5, jsonReader.getPosition().getCol());

        assertEquals('\n', jsonReader.next());
        assertEquals(3, jsonReader.getPosition().getRow());
        assertEquals(1, jsonReader.getPosition().getCol());

        assertEquals('W', jsonReader.next());
        assertEquals(4, jsonReader.getPosition().getRow());
        assertEquals(1, jsonReader.getPosition().getCol());
        assertEquals('o', jsonReader.next());
        assertEquals(4, jsonReader.getPosition().getRow());
        assertEquals(2, jsonReader.getPosition().getCol());
        assertEquals('r', jsonReader.next());
        assertEquals(4, jsonReader.getPosition().getRow());
        assertEquals(3, jsonReader.getPosition().getCol());
        assertEquals('l', jsonReader.next());
        assertEquals(4, jsonReader.getPosition().getRow());
        assertEquals(4, jsonReader.getPosition().getCol());
        assertEquals('d', jsonReader.next());
        assertEquals(4, jsonReader.getPosition().getRow());
        assertEquals(5, jsonReader.getPosition().getCol());
        assertEquals('\n', jsonReader.next());
        assertEquals(4, jsonReader.getPosition().getRow());
        assertEquals(6, jsonReader.getPosition().getCol());

        assertEquals('\u0000', jsonReader.next());
        assertEquals(5, jsonReader.getPosition().getRow());
        assertEquals(1, jsonReader.getPosition().getCol());
    }

    @Test
    void testBack() {
        StringReader reader = new StringReader("Hello");
        JsonBufferedReader jsonReader = new JsonBufferedReader(reader);

        assertEquals('H', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(1, jsonReader.getPosition().getCol());
        assertEquals('e', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(2, jsonReader.getPosition().getCol());
        assertEquals('l', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(3, jsonReader.getPosition().getCol());

        jsonReader.back();
        assertEquals('l', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(3, jsonReader.getPosition().getCol());

        assertEquals('l', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(4, jsonReader.getPosition().getCol());
        assertEquals('o', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(5, jsonReader.getPosition().getCol());

        assertEquals('\u0000', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(6, jsonReader.getPosition().getCol());
    }

    @Test
    void testDoubleBack() {
        StringReader reader = new StringReader("Hello");
        JsonBufferedReader jsonReader = new JsonBufferedReader(reader);

        assertEquals('H', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(1, jsonReader.getPosition().getCol());
        assertEquals('e', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(2, jsonReader.getPosition().getCol());
        assertEquals('l', jsonReader.next());
        assertEquals(1, jsonReader.getPosition().getRow());
        assertEquals(3, jsonReader.getPosition().getCol());

        jsonReader.back();

        assertThrows(IllegalStateException.class, jsonReader::back);
    }

    @Test
    void testFreshBack() {
        StringReader reader = new StringReader("Hello");
        JsonBufferedReader jsonReader = new JsonBufferedReader(reader);
        assertThrows(IllegalStateException.class, jsonReader::back);
    }

    @Test
    void testIOExceptionInReader() {
        Reader reader = new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                throw new IOException("test");
            }

            @Override
            public void close() {

            }
        };

        JsonBufferedReader jsonReader = new JsonBufferedReader(reader);
        assertThrows(ParsingException.class, jsonReader::next);
    }

}