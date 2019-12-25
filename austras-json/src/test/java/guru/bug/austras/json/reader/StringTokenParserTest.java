package guru.bug.austras.json.reader;

import org.junit.jupiter.api.Test;

import static guru.bug.austras.json.reader.StringTokenParser.convertHexToDec;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringTokenParserTest {

    @Test
    void testConvertHexToDec() {
        assertEquals(0, convertHexToDec('0'));
        assertEquals(1, convertHexToDec('1'));
        assertEquals(2, convertHexToDec('2'));
        assertEquals(3, convertHexToDec('3'));
        assertEquals(4, convertHexToDec('4'));
        assertEquals(5, convertHexToDec('5'));
        assertEquals(6, convertHexToDec('6'));
        assertEquals(7, convertHexToDec('7'));
        assertEquals(8, convertHexToDec('8'));
        assertEquals(9, convertHexToDec('9'));
        assertEquals(10, convertHexToDec('A'));
        assertEquals(11, convertHexToDec('B'));
        assertEquals(12, convertHexToDec('C'));
        assertEquals(13, convertHexToDec('D'));
        assertEquals(14, convertHexToDec('E'));
        assertEquals(15, convertHexToDec('F'));
        assertEquals(10, convertHexToDec('a'));
        assertEquals(11, convertHexToDec('b'));
        assertEquals(12, convertHexToDec('c'));
        assertEquals(13, convertHexToDec('d'));
        assertEquals(14, convertHexToDec('e'));
        assertEquals(15, convertHexToDec('f'));

        //noinspection ResultOfMethodCallIgnored
        assertThrows(IllegalArgumentException.class, () -> convertHexToDec('x'));
    }

}