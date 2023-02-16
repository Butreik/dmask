package com.butreik.dmask.core;

import java.util.Collection;

/**
 * A utility class for performing common assertions.
 *
 * @author Vladimir Rudnev
 */
public class Assert {

    private Assert() {
    }

    /**
     * Checks that the given object is not null.
     *
     * @param object the object to check for nullity
     * @return the input object if it is not null
     * @throws IllegalArgumentException if the input object is null
     */
    public static <T> T assertNotNull(T object) {
        if (object == null) {
            throw new IllegalArgumentException("Object required to be not null");
        }
        return object;
    }

    /**
     * Checks that the given string is not null or empty (after trimming).
     *
     * @param string the string to check for nullity or emptiness
     * @return the input string if it is not null or empty
     * @throws IllegalArgumentException if the input string is null or empty
     */
    public static String assertNotEmpty(String string) {
        if (string == null || string.trim().length() == 0) {
            throw new IllegalArgumentException("String must be not null and not empty");
        }
        return string;
    }

    /**
     * Checks that the given collection is not null or empty.
     *
     * @param collection the collection to check for nullity or emptiness
     * @return the input collection if it is not null or empty
     * @throws IllegalArgumentException if the input collection is null or empty
     */
    public static <T> Collection<T> assertNotEmpty(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("Collection must be not null and not empty");
        }
        return collection;
    }

    /**
     * Checks that the given condition is true.
     *
     * @param condition the condition to check for truth
     * @throws IllegalArgumentException if the input condition is false
     */
    public static void assertTrue(boolean condition) {
        if (condition) {
            return;
        }
        throw new IllegalArgumentException("Condition expected to be true");
    }
}
