/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class StringTokenParserTest {

    @Test
    void testConvertHexToDec() {
        Assertions.assertEquals(0, StringTokenParser.convertHexToDec('0'));
        Assertions.assertEquals(1, StringTokenParser.convertHexToDec('1'));
        Assertions.assertEquals(2, StringTokenParser.convertHexToDec('2'));
        Assertions.assertEquals(3, StringTokenParser.convertHexToDec('3'));
        Assertions.assertEquals(4, StringTokenParser.convertHexToDec('4'));
        Assertions.assertEquals(5, StringTokenParser.convertHexToDec('5'));
        Assertions.assertEquals(6, StringTokenParser.convertHexToDec('6'));
        Assertions.assertEquals(7, StringTokenParser.convertHexToDec('7'));
        Assertions.assertEquals(8, StringTokenParser.convertHexToDec('8'));
        Assertions.assertEquals(9, StringTokenParser.convertHexToDec('9'));
        Assertions.assertEquals(10, StringTokenParser.convertHexToDec('A'));
        Assertions.assertEquals(11, StringTokenParser.convertHexToDec('B'));
        Assertions.assertEquals(12, StringTokenParser.convertHexToDec('C'));
        Assertions.assertEquals(13, StringTokenParser.convertHexToDec('D'));
        Assertions.assertEquals(14, StringTokenParser.convertHexToDec('E'));
        Assertions.assertEquals(15, StringTokenParser.convertHexToDec('F'));
        Assertions.assertEquals(10, StringTokenParser.convertHexToDec('a'));
        Assertions.assertEquals(11, StringTokenParser.convertHexToDec('b'));
        Assertions.assertEquals(12, StringTokenParser.convertHexToDec('c'));
        Assertions.assertEquals(13, StringTokenParser.convertHexToDec('d'));
        Assertions.assertEquals(14, StringTokenParser.convertHexToDec('e'));
        Assertions.assertEquals(15, StringTokenParser.convertHexToDec('f'));

        //noinspection ResultOfMethodCallIgnored
        assertThrows(IllegalArgumentException.class, () -> StringTokenParser.convertHexToDec('x'));
    }

}