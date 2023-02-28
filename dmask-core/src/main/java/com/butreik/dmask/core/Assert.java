package com.butreik.dmask.core;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * Checks if the given object is not null, and throws an IllegalArgumentException with the message provided by the
     * supplier if the object is null.
     *
     * @param object the object to checked for nullity
     * @param message a supplier function that provides the exception message if the object is null
     * @return the input object if it is not null
     * @throws IllegalArgumentException if the input object is null
     */
    public static <T> T assertNotNull(T object, Supplier<String> message) {
        if (object == null) {
            throw new IllegalArgumentException(message.get());
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

    private static final Pattern KEBAB_CASE_PATTERN = Pattern.compile("[a-z0-9]+(?:-[a-z0-9]+)*");

    /**
     * Checks that the given string is in kebab-case format.
     *
     * @param string the string to be checked
     * @param message a supplier function that provides the exception message if the object is null
     * @throws IllegalArgumentException if the string is not in kebab-case format
     * @return the same input string if it is in kebab-case format
     */
    public static String assertKebabCase(String string, Supplier<String> message) {
        Matcher matcher = KEBAB_CASE_PATTERN.matcher(string);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(message.get());
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
