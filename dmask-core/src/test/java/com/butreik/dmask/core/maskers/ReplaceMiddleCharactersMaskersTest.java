package com.butreik.dmask.core.maskers;

import org.junit.jupiter.api.Test;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReplaceMiddleCharactersMaskersTest {


    @Test
    public void replaceMiddleTest() {
        //given
        ReplaceMiddleCharactersMaskers masker = ReplaceMiddleCharactersMaskers.builder()
                .name("TestName").order(100).replacementChar('#').from(2).to(7).build();
        //when
        String result = masker.mask("testString");
        //then
        assertEquals("te#####ing", result);
        assertEquals("TestName", masker.getName());
        assertEquals(100, masker.getOrder());
        //when
        result = masker.mask("String");
        //then
        assertEquals("St####", result);
        //when
        result = masker.mask("St");
        //then
        assertEquals("St", result);
    }

    @Test
    public void replaceSuffixTest() {
        //given
        ReplaceMiddleCharactersMaskers masker = ReplaceMiddleCharactersMaskers.builder()
                .name("TestName").from(2).build();
        //when
        String result = masker.mask("testString");
        //then
        assertEquals("te********", result);
    }

    @Test
    public void replacePrefixTest() {
        //given
        ReplaceMiddleCharactersMaskers masker = ReplaceMiddleCharactersMaskers.builder()
                .name("TestName").from(0).to(5).build();
        //when
        String result = masker.mask("testString");
        //then
        assertEquals("*****tring", result);
    }

    @Test
    public void excludedCharsTest() {
        //given
        ReplaceMiddleCharactersMaskers masker = ReplaceMiddleCharactersMaskers.builder()
                .name("TestName").from(2).to(10).excludedChars(singleton('-')).excludedChar(' ').build();
        //when
        String result = masker.mask("test-S tring");
        //then
        assertEquals("te**-* ***ng", result);
    }

    @Test
    public void assertTest() {
        assertThrows(IllegalArgumentException.class, () -> ReplaceMiddleCharactersMaskers.builder().build());
        assertThrows(IllegalArgumentException.class, () ->
                ReplaceMiddleCharactersMaskers.builder().name("TestName").from(-1).build());
        assertThrows(IllegalArgumentException.class, () ->
                ReplaceMiddleCharactersMaskers.builder().name("TestName").from(5).to(2).build());
    }
}
