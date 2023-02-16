package com.butreik.dmask.core.maskers;

import static com.butreik.dmask.core.Assert.assertNotEmpty;
import static com.butreik.dmask.core.Assert.assertNotNull;

/**
 * A string masker that replaces the original string with a fixed replacement string.
 *
 * @author Vladimir Rudnev
 */
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

    /**
     * Creates a new builder instance to build a replace string masker.
     *
     * @return a new builder instance.
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

    @Override
    public String mask(String input) {
        return replacementString;
    }

    /**
     * A builder for the {@link ReplaceStringMasker}.
     */
    public static class Builder {
        private String name;
        private int order;
        private String replacementString;
        private char replacementChar = DEFAULT_REPLACEMENT_CHAR;
        private int replacementCharLength = 6;

        Builder() {
        }

        /**
         * Sets the name for the masker.
         *
         * @param name the name for the masker.
         * @return the builder instance.
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the order of the masker.
         *
         * @param order the order of the masker.
         * @return the builder instance.
         */
        public Builder order(int order) {
            this.order = order;
            return this;
        }

        /**
         * Sets the replacement string for the masker.
         *
         * @param replacementString the replacement string for the masker.
         * @return the builder instance.
         */
        public Builder replacementString(String replacementString) {
            this.replacementString = replacementString;
            return this;
        }

        /**
         * Sets the replacement character for the masker.
         *
         * @param replacementChar the replacement character for the masker.
         * @return the builder instance.
         */
        public Builder replacementChar(char replacementChar) {
            this.replacementChar = replacementChar;
            return this;
        }

        /**
         * Sets the length of the replacement character sequence.
         *
         * @param replacementCharLength the length of the replacement character sequence.
         * @return the builder instance.
         */
        public Builder replacementCharLength(int replacementCharLength) {
            this.replacementCharLength = replacementCharLength;
            return this;
        }

        /**
         * Builds a new instance of {@link ReplaceStringMasker}.
         *
         * @return a new instance of {@link ReplaceStringMasker}.
         */
        public ReplaceStringMasker build() {
            if (replacementString == null) {
                replacementString = String.valueOf(replacementChar).repeat(replacementCharLength);
            }
            return new ReplaceStringMasker(name, order, replacementString);
        }

    }
}
