package com.butreik.dmask.core.maskers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RemoveMaskerTest {

    @Test
    public void test() {
        //given
        RemoveMasker masker = RemoveMasker.INSTANCE;
        //when
        Object result = masker.mask(new Object());
        //then
        assertNull(result);
        assertEquals("RemoveMasker", masker.getName());
        assertEquals(Integer.MIN_VALUE, masker.getOrder());
        assertEquals(Object.class, masker.getJsonFieldType());
    }
}
