package com.butreik.dmask.core.validate;

import java.util.Collection;

public class Assert {

    private Assert() {
    }

    public static <T> T assertNotNull(T object) {
        if (object == null) {
            throw new AssertException("Object required to be not null");
        }
        return object;
    }

    public static String assertNotEmpty(String string) {
        if (string == null || string.trim().length() == 0) {
            throw new AssertException("String must be not null and not empty");
        }
        return string;
    }

    public static <T> Collection<T> assertNotEmpty(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new AssertException("Collection must be not null and not empty");
        }
        return collection;
    }

    public static void assertTrue(boolean condition) {
        if (condition) {
            return;
        }
        throw new AssertException("Condition expected to be true");
    }
}
