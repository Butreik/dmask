package com.butreik.dmask.core;

import static com.butreik.dmask.core.Assert.assertKebabCase;
import static com.butreik.dmask.core.Assert.assertNotNull;

/**
 * This class represents a masker to be applied to JSON data.
 *
 * @author Vladimir Rudnev
 */
public class Masker {

    private static final String DEFAULT_NAME  = "UNDEFINED";

    /**
     * The name of the masker.
     */
    private final String name;

    /**
     * The order in which the masker should be applied.
     */
    private final int order;

    /**
     * The function to be applied to the JSON data to mask it.
     */
    private final MapFunction mapFunction;

    /**
     * Constructs a new Masker object with the specified name, order and map function.
     *
     * @param name         The name of the masker.
     * @param order        The order in which the masker should be applied.
     * @param mapFunction  The function to be applied to the JSON data to mask it.
     */
    private Masker(String name, int order, MapFunction mapFunction) {
        this.name = name;
        this.order = order;
        this.mapFunction = mapFunction;
    }

    /**
     * Returns the name of the masker.
     *
     * @return The name of the masker.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the order in which the masker should be applied.
     *
     * @return The order in which the masker should be applied.
     */
    public int getOrder() {
        return order;
    }

    /**
     * Returns the function to be applied to the JSON data to mask it.
     *
     * @return The function to be applied to the JSON data to mask it.
     */
    public MapFunction getMapFunction() {
        return mapFunction;
    }

    /**
     * Returns a new builder instance for Masker.
     *
     * @return A new builder instance for Masker.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder class for constructing Masker instances.
     */
    public static class Builder {
        private String name;
        private int order;
        private MapFunction mapFunction;

        private Builder() {}

        /**
         * Sets the name of the masker to be constructed.
         *
         * @param name The name of the masker.
         * @return The builder instance.
         */
        public Builder name(String name) {
            assertKebabCase(name, () -> "Name of masker must be in kebab-case format");
            this.name = name;
            return this;
        }

        /**
         * Sets the order in which the masker should be applied.
         *
         * @param order The order in which the masker should be applied.
         * @return The builder instance.
         */
        public Builder order(int order) {
            this.order = order;
            return this;
        }

        /**
         * Sets the function to be applied to the JSON data to mask it.
         *
         * @param mapFunction The function to be applied to the JSON data to mask it.
         * @return The builder instance.
         */
        public Builder mapFunction(MapFunction mapFunction) {
            assertNotNull(mapFunction);
            this.mapFunction = mapFunction;
            return this;
        }

        /**
         * Builds and returns a new Masker instance.
         *
         * @return A new Masker instance.
         */
        public Masker build() {
            return new Masker(name != null ? name : DEFAULT_NAME, order, mapFunction);
        }
    }

}
