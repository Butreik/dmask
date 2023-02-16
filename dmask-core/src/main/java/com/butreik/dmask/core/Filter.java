package com.butreik.dmask.core;

import com.butreik.dmask.core.maskers.Masker;

import static com.butreik.dmask.core.Assert.assertNotEmpty;
import static com.butreik.dmask.core.Assert.assertNotNull;

/**
 * Represents a filter that is used to mask a specified JSON path with a given masker.
 *
 * @author Vladimir Rudnev
 */
public class Filter {

    /**
     * The JSON path used for filtering.
     */
    private final String jsonPath;

    /**
     * The masker used to apply the mask to the filtered data.
     */
    private final Masker<?> masker;

    private Filter(String jsonPath, Masker<?> masker) {
        assertNotEmpty(jsonPath);
        assertNotNull(masker);
        this.jsonPath = jsonPath;
        this.masker = masker;
    }

    /**
     * Returns a new builder for creating Filter objects.
     *
     * @return the builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the JSON path used for filtering.
     *
     * @return the JSON path.
     */
    public String getJsonPath() {
        return jsonPath;
    }

    /**
     * Returns the masker used to apply the mask to the filtered data.
     *
     * @return the masker.
     */
    public Masker<?> getMasker() {
        return masker;
    }

    /**
     * A builder for creating a list of {@link Filter} objects that specify JSON paths to be masked by a given masker.
     */
    public static class Builder {
        private String jsonPath;
        private Masker<?> masker;

        private Builder() {
        }

        /**
         * Sets the JSON path used for filtering.
         *
         * @param jsonPath the JSON path to set.
         * @return the builder.
         */
        public Builder jsonPath(String jsonPath) {
            this.jsonPath = jsonPath;
            return this;
        }

        /**
         * Sets the masker used to apply the mask to the filtered data.
         *
         * @param masker the masker to set.
         * @return the builder.
         */
        public Builder masker(Masker<?> masker) {
            this.masker = masker;
            return this;
        }

        /**
         * Builds a new Filter object using the parameters set on this builder.
         *
         * @return the new Filter object.
         */
        public Filter build() {
            return new Filter(jsonPath, masker);
        }
    }
}
