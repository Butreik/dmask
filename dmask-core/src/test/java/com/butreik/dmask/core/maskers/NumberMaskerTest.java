package com.butreik.dmask.core.maskers;

import com.butreik.dmask.core.validate.AssertException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NumberMaskerTest {

    @Test
    public void test() {
        //given
        NumberMasker masker = NumberMasker.builder().name("TestName").order(100).replacementNumber(-1).build();
        //when
        Number result = masker.mask(BigDecimal.ONE);
        //then
        assertEquals(-1, result);
        assertEquals("TestName", masker.getName());
        assertEquals(100, masker.getOrder());
    }

    @Test
    public void assertTest() {
        assertThrows(AssertException.class, () -> NumberMasker.builder().build());
        assertThrows(AssertException.class, () -> NumberMasker.builder().name(" ").build());
        assertThrows(AssertException.class, () ->
                NumberMasker.builder().name("TestName").replacementNumber(null).build());
    }
}
