package com.butreik.dmask.core.maskers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReplaceStringMaskerTest {

    @Test
    public void replacementCharTest() {
        //given
        ReplaceStringMasker masker = ReplaceStringMasker.builder()
                .name("TestName")
                .order(100)
                .replacementChar('#')
                .replacementCharLength(3)
                .build();
        //when
        String result = masker.mask("testString");
        //then
        assertEquals("###", result);
        assertEquals("TestName", masker.getName());
        assertEquals(100, masker.getOrder());
    }

    @Test
    public void replacementStringTest() {
        //given
        ReplaceStringMasker masker = ReplaceStringMasker.builder()
                .name("TestName")
                .order(100)
                .replacementString("replacementString")
                .build();
        //when
        String result = masker.mask("testString");
        //then
        assertEquals("replacementString", result);
        assertEquals("TestName", masker.getName());
        assertEquals(100, masker.getOrder());
    }

    @Test
    public void assertTest() {
        assertThrows(IllegalArgumentException.class, () -> ReplaceStringMasker.builder().build());
    }
}
