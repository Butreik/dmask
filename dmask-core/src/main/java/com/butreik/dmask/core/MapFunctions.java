package com.butreik.dmask.core;

import java.util.Optional;

/**
 * @author Vladimir Rudnev
 */
public interface MapFunctions {

    char DEFAULT_REPLACEMENT_CHAR = '*';
    String DEFAULT_REPLACEMENT_STRING = "******";
    Number DEFAULT_REPLACEMENT_NUMBER = 0;

    /**
     * Returns a MapFunction that masks email addresses by replacing the characters before the "@" symbol with asterisks.
     * @return a MapFunction that masks email addresses
     */
    static MapFunction maskEmail() {
        return input -> Optional.ofNullable(input)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(str -> {
                    int atIndex = str.indexOf('@');
                    if (atIndex == -1) {
                        return (Object) str;
                    }
                    String domain = str.substring(atIndex);
                    String prefix = str.substring(0, atIndex);
                    return String.valueOf(DEFAULT_REPLACEMENT_CHAR).repeat(prefix.length()) + domain;
                })
                .orElse(input);
    }

    /**
     * Returns a MapFunction that masks all characters in a string except the first character by replacing them with asterisks.
     * @return a MapFunction that masks all characters except the first one
     */
    static MapFunction maskExceptFirstCharacter() {
        return input -> Optional.ofNullable(input)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .filter(str -> str.length() > 1)
                .map(str -> (Object) (str.charAt(0) + String.valueOf(DEFAULT_REPLACEMENT_CHAR).repeat(str.length() - 1)))
                .orElse(input);
    }

    /**
     * Returns a MapFunction that masks the characters in a string between the "from" and "to" indexes by replacing them with asterisks.
     * @param from the starting index of the characters to be masked
     * @param to the ending index (exclusive) of the characters to be masked
     * @return a MapFunction that masks characters between the "from" and "to" indexes
     */
    static MapFunction maskMiddleCharactersMaskers(int from, int to) {
        return input -> Optional.ofNullable(input)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .filter(str -> str.length() > from)
                .map(str -> {
                    String prefix = from > 0 ? str.substring(0, from) : "";
                    String suffix = to < str.length() ? str.substring(to) : "";
                    String middle = String.valueOf(DEFAULT_REPLACEMENT_CHAR).repeat(Math.min(str.length(), to) - from);
                    return (Object) (prefix + middle + suffix);
                })
                .orElse(input);
    }
}
