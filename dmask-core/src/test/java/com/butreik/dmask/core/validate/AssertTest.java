package com.butreik.dmask.core.validate;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.butreik.dmask.core.Assert.assertNotEmpty;
import static com.butreik.dmask.core.Assert.assertNotNull;
import static com.butreik.dmask.core.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertTest {

    @Test
    public void assertNotNullTest() {
        assertNotNull(new Object());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> assertNotNull(null));
        assertEquals("Object required to be not null", exception.getMessage());
    }

    @Test
    public void assertNotEmptyStringTest() {
        assertNotEmpty("str");
        String nullStr = null;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> assertNotEmpty(nullStr));
        assertEquals("String must be not null and not empty", exception.getMessage());
        exception = assertThrows(IllegalArgumentException.class, () -> assertNotEmpty(" "));
        assertEquals("String must be not null and not empty", exception.getMessage());
    }

    @Test
    public void assertNotEmptyCollectionTest() {
        assertNotEmpty(List.of(new Object()));
        List<Object> nullList = null;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> assertNotEmpty(nullList));
        assertEquals("Collection must be not null and not empty", exception.getMessage());
        exception = assertThrows(IllegalArgumentException.class, () -> assertNotEmpty(new ArrayList<>()));
        assertEquals("Collection must be not null and not empty", exception.getMessage());
    }

    @Test
    public void assertTrueTest() {
        assertTrue(true);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> assertTrue(false));
        assertEquals("Condition expected to be true", exception.getMessage());
    }
}
