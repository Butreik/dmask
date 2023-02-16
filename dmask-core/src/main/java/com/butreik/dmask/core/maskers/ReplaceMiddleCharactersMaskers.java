package com.butreik.dmask.core.maskers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.butreik.dmask.core.Assert.assertNotEmpty;
import static com.butreik.dmask.core.Assert.assertNotNull;
import static com.butreik.dmask.core.Assert.assertTrue;

/**
 * A masker that replaces middle characters of a string with a given character,
 * while optionally excluding certain characters from replacement.
 * <p>
 * The masker takes a substring of the input string, starting from the `from` index
 * (inclusive) and ending at the `to` index (exclusive), and replaces all characters
 * in this substring, excluding the ones that appear in the `excludedChars` set,
 * with the `replacementChar`. The rest of the input string is left unchanged.
 * <p>
 * If `to` is greater than the length of the input string, the masker only replaces
 * characters up to the end of the input string.
 *
 * @author Vladimir Rudnev
 */
public class ReplaceMiddleCharactersMaskers extends AbstractStringMasker {

    private final String name;
    private final int order;
    private final char replacementChar;
    private final int from;
    private final int to;
    private final Set<Character> excludedChars;
    private final boolean hasExcludedChars;

    private ReplaceMiddleCharactersMaskers(String name, int order, char replacementChar, int from, int to, Set<Character> excludedChars) {
        assertNotEmpty(name);
        assertNotNull(excludedChars);
        assertTrue(from >= 0);
        assertTrue(from <= to);
        this.name = name;
        this.order = order;
        this.replacementChar = replacementChar;
        this.from = from;
        this.to = to;
        this.excludedChars = Collections.unmodifiableSet(excludedChars);
        this.hasExcludedChars = !excludedChars.isEmpty();
    }

    /**
     * Returns a builder for creating instances of `ReplaceMiddleCharactersMaskers`.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrder() {
        return order;
    }

    /**
     * Masks the input string by replacing its middle characters with the `replacementChar`,
     * while optionally excluding certain characters from replacement.
     *
     * @param input the input string to be masked
     * @return the masked string
     */
    @Override
    public String mask(String input) {
        if (from >= input.length()) {
            return input;
        }
        String prefix = from > 0 ? input.substring(0, from) : "";
        String suffix = to < input.length() ? input.substring(to) : "";

        if (!hasExcludedChars) {
            String middle = String.valueOf(replacementChar).repeat(Math.min(input.length(), to) - from);
            return prefix + middle + suffix;
        } else {
            StringBuilder builder = new StringBuilder();
            for (int idx = from; idx < Math.min(input.length(), to); idx++) {
                char oldChar = input.charAt(idx);
                char newChar = excludedChars.contains(oldChar) ? oldChar : replacementChar;
                builder.append(newChar);
            }

            return prefix + builder + suffix;
        }
    }

    public static class Builder {
        private final Set<Character> excludedChars = new HashSet<>();
        private String name;
        private int order;
        private char replacementChar = DEFAULT_REPLACEMENT_CHAR;
        private int from;
        private int to = Integer.MAX_VALUE;

        Builder() {
        }

        /**
         * Sets the name of the masker.
         *
         * @param name the name of the masker
         * @return the builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the order of the masker.
         *
         * @param order the order of the masker
         * @return the builder
         */
        public Builder order(int order) {
            this.order = order;
            return this;
        }

        /**
         * Sets the character to use as a replacement for the middle characters.
         *
         * @param replacementChar the replacement character
         * @return the builder
         */
        public Builder replacementChar(char replacementChar) {
            this.replacementChar = replacementChar;
            return this;
        }

        /**
         * Sets the start index of the middle characters to replace.
         *
         * @param from the start index
         * @return the builder
         */
        public Builder from(int from) {
            this.from = from;
            return this;
        }

        /**
         * Sets the end index of the middle characters to replace.
         *
         * @param to the end index
         * @return the builder
         */
        public Builder to(int to) {
            this.to = to;
            return this;
        }

        /**
         * Adds a character to the set of excluded characters, which will not be replaced.
         *
         * @param excludedChar the excluded character
         * @return the builder
         */
        public Builder excludedChar(char excludedChar) {
            this.excludedChars.add(excludedChar);
            return this;
        }


        /**
         * Adds a set of characters to the set of excluded characters, which will not be replaced.
         *
         * @param excludedChars the excluded characters
         * @return the builder
         */
        public Builder excludedChars(Set<Character> excludedChars) {
            this.excludedChars.addAll(excludedChars);
            return this;
        }

        /**
         * Builds a new instance of the masker.
         *
         * @return the new instance of the masker
         */
        public ReplaceMiddleCharactersMaskers build() {
            return new ReplaceMiddleCharactersMaskers(name, order, replacementChar, from, to, excludedChars);
        }
    }
}
