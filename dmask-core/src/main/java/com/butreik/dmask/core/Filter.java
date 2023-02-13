package com.butreik.dmask.core;

import com.butreik.dmask.core.maskers.Masker;

import static com.butreik.dmask.core.validate.Assert.assertNotEmpty;
import static com.butreik.dmask.core.validate.Assert.assertNotNull;

public class Filter {

    private final String jsonPath;

    private final Masker<?> masker;

    private Filter(String jsonPath, Masker<?> masker) {
        assertNotEmpty(jsonPath);
        assertNotNull(masker);
        this.jsonPath = jsonPath;
        this.masker = masker;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public Masker<?> getMasker() {
        return masker;
    }

    public static class Builder {
        private String jsonPath;
        private Masker<?> masker;

        private Builder() {
        }

        public Builder jsonPath(String jsonPath) {
            this.jsonPath = jsonPath;
            return this;
        }

        public Builder masker(Masker<?> masker) {
            this.masker = masker;
            return this;
        }

        public Filter build() {
            return new Filter(jsonPath, masker);
        }
    }
}
