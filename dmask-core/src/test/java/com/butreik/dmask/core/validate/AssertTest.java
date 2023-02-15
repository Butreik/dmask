package com.butreik.dmask.core.validate;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.butreik.dmask.core.validate.Assert.assertNotEmpty;
import static com.butreik.dmask.core.validate.Assert.assertNotNull;
import static com.butreik.dmask.core.validate.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertTest {

    @Test
    public void assertNotNullTest() {
        assertNotNull(new Object());
        AssertException exception = assertThrows(AssertException.class, () -> assertNotNull(null));
        assertEquals("Object required to be not null", exception.getMessage());
    }

    @Test
    public void assertNotEmptyStringTest() {
        assertNotEmpty("str");
        String nullStr = null;
        AssertException exception = assertThrows(AssertException.class, () -> assertNotEmpty(nullStr));
        assertEquals("String must be not null and not empty", exception.getMessage());
        exception = assertThrows(AssertException.class, () -> assertNotEmpty(" "));
        assertEquals("String must be not null and not empty", exception.getMessage());
    }

    @Test
    public void assertNotEmptyCollectionTest() {
        assertNotEmpty(List.of(new Object()));
        List<Object> nullList = null;
        AssertException exception = assertThrows(AssertException.class, () -> assertNotEmpty(nullList));
        assertEquals("Collection must be not null and not empty", exception.getMessage());
        exception = assertThrows(AssertException.class, () -> assertNotEmpty(new ArrayList<>()));
        assertEquals("Collection must be not null and not empty", exception.getMessage());
    }

    @Test
    public void assertTrueTest() {
        assertTrue(true);
        AssertException exception = assertThrows(AssertException.class, () -> assertTrue(false));
        assertEquals("Condition expected to be true", exception.getMessage());
    }
}
