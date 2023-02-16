package com.butreik.dmask.core.maskers;

import static com.butreik.dmask.core.Assert.assertNotEmpty;
import static com.butreik.dmask.core.Assert.assertNotNull;

/**
 * A masker that replaces numbers with a given replacement number.
 *
 * @author Vladimir Rudnev
 */
public class NumberMasker implements Masker<Number> {

    private final String name;
    private final int order;
    private final Number replacementNumber;

    NumberMasker(String name, int order, Number replacementNumber) {
        assertNotEmpty(name);
        assertNotNull(replacementNumber);
        this.name = name;
        this.order = order;
        this.replacementNumber = replacementNumber;
    }

    /**
     * Returns a new builder for the NumberMasker.
     *
     * @return a new builder for the NumberMasker
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
    public Class<Number> getJsonFieldType() {
        return Number.class;
    }

    @Override
    public Number mask(Number input) {
        return replacementNumber;
    }

    /**
     * A builder for the NumberMasker.
     *
     * @author Vladimir Rudnev
     */
    public static class Builder {
        private String name;
        private int order;
        private Number replacementNumber = 0;

        private Builder() {
        }

        /**
         * Sets the name of the masker.
         *
         * @param name the name of the masker
         * @return this builder instance
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the order of the masker.
         *
         * @param order the order of the masker
         * @return this builder instance
         */
        public Builder order(int order) {
            this.order = order;
            return this;
        }

        /**
         * Sets the replacement number.
         *
         * @param replacementNumber the replacement number to use
         * @return this builder instance
         */
        public Builder replacementNumber(Number replacementNumber) {
            this.replacementNumber = replacementNumber;
            return this;
        }

        /**
         * Builds a new NumberMasker with the given settings.
         *
         * @return a new NumberMasker with the given settings
         */
        public NumberMasker build() {
            return new NumberMasker(name, order, replacementNumber);
        }
    }
}
