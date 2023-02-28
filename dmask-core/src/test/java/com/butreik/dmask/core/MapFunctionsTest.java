package com.butreik.dmask.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapFunctionsTest {

    @Test
    public void maskEmailTest() {
        assertEquals("notemail", MapFunctions.maskEmail().map("notemail"));
        assertEquals("****@domain.com", MapFunctions.maskEmail().map("name@domain.com"));
    }

    @Test
    public void maskExceptFirstCharacterTest() {
        assertEquals("S*****", MapFunctions.maskExceptFirstCharacter().map("String"));
        assertEquals("S", MapFunctions.maskExceptFirstCharacter().map("S"));
    }

    @Test
    public void maskMiddleCharactersMaskersTest() {
        assertEquals("**********", MapFunctions.maskMiddleCharactersMaskers(0, 15).map("testString"));
        assertEquals("****String", MapFunctions.maskMiddleCharactersMaskers(0, 4).map("testString"));
        assertEquals("test***ing", MapFunctions.maskMiddleCharactersMaskers(4, 7).map("testString"));
        assertEquals("testStr***", MapFunctions.maskMiddleCharactersMaskers(7, 15).map("testString"));
        assertEquals("testString", MapFunctions.maskMiddleCharactersMaskers(10, 15).map("testString"));
    }
}
