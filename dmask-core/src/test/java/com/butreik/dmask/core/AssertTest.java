package com.butreik.dmask.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.butreik.dmask.core.Assert.assertKebabCase;
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
        assertNotNull(new Object(), () -> "message");
        exception = assertThrows(IllegalArgumentException.class, () -> assertNotNull(null, () -> "message"));
        assertEquals("message", exception.getMessage());
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
    public void assertKebabCaseTest() {
        assertKebabCase("kebab-case", () -> "message");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> assertKebabCase("KEBAB_CASE", () -> "message"));
        assertEquals("message", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class,
                () -> assertKebabCase("KebabCase", () -> "message"));
        assertEquals("message", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class,
                () -> assertKebabCase("kebabCase", () -> "message"));
        assertEquals("message", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class,
                () -> assertKebabCase("kebab_case", () -> "message"));
        assertEquals("message", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class,
                () -> assertKebabCase("kebab case", () -> "message"));
        assertEquals("message", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class,
                () -> assertKebabCase("Kebab Case", () -> "message"));
        assertEquals("message", exception.getMessage());
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
