package com.butreik.dmask.core.maskers;

import static com.butreik.dmask.core.validate.Assert.assertNotEmpty;
import static com.butreik.dmask.core.validate.Assert.assertNotNull;

public class ReplaceStringMasker extends AbstractStringMasker {

    private final String name;
    private final int order;
    private final String replacementString;

    private ReplaceStringMasker(String name, int order, String replacementString) {
        assertNotEmpty(name);
        assertNotNull(replacementString);
        this.name = name;
        this.order = order;
        this.replacementString = replacementString;
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
        return replacementString;
    }

    public static class Builder {
        private String name;
        private int order;
        private String replacementString;
        private char replacementChar = REPLACEMENT_CHAR;
        private int replacementCharLength = 6;

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

        public Builder replacementString(String replacementString) {
            this.replacementString = replacementString;
            return this;
        }

        public Builder replacementChar(char replacementChar) {
            this.replacementChar = replacementChar;
            return this;
        }

        public Builder replacementCharLength(int replacementCharLength) {
            this.replacementCharLength = replacementCharLength;
            return this;
        }

        public ReplaceStringMasker build() {
            if (replacementString == null) {
                replacementString = String.valueOf(replacementChar).repeat(replacementCharLength);
            }
            return new ReplaceStringMasker(name, order, replacementString);
        }

    }
}
