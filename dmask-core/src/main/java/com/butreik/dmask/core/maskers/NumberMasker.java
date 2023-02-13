package com.butreik.dmask.core.maskers;

import static com.butreik.dmask.core.validate.Assert.assertNotEmpty;
import static com.butreik.dmask.core.validate.Assert.assertNotNull;

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

    public static class Builder {
        private String name;
        private int order;
        private Number replacementNumber = 0;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder order(int order) {
            this.order = order;
            return this;
        }

        public Builder replacementNumber(Number replacementNumber) {
            this.replacementNumber = replacementNumber;
            return this;
        }

        public NumberMasker build() {
            return new NumberMasker(name, order, replacementNumber);
        }
    }
}
