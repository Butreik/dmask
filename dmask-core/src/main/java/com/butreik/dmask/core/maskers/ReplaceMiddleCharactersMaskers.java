package com.butreik.dmask.core.maskers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.butreik.dmask.core.validate.Assert.*;

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
        private char replacementChar = REPLACEMENT_CHAR;
        private int from;
        private int to = Integer.MAX_VALUE;

        Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder order(int order) {
            this.order = order;
            return this;
        }

        public Builder replacementChar(char replacementChar) {
            this.replacementChar = replacementChar;
            return this;
        }

        public Builder from(int from) {
            this.from = from;
            return this;
        }

        public Builder to(int to) {
            this.to = to;
            return this;
        }

        public Builder excludedChar(char excludedChar) {
            this.excludedChars.add(excludedChar);
            return this;
        }

        public Builder excludedChars(Set<Character> excludedChars) {
            this.excludedChars.addAll(excludedChars);
            return this;
        }

        public ReplaceMiddleCharactersMaskers build() {
            return new ReplaceMiddleCharactersMaskers(name, order, replacementChar, from, to, excludedChars);
        }
    }
}
