package com.butreik.dmask.core;

import com.butreik.dmask.core.maskers.Masker;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static com.butreik.dmask.core.Assert.assertNotEmpty;
import static com.butreik.dmask.core.Assert.assertNotNull;
import static com.butreik.dmask.core.maskers.Maskers.REMOVE_MASKER;
import static com.jayway.jsonpath.Option.ALWAYS_RETURN_LIST;
import static com.jayway.jsonpath.Option.SUPPRESS_EXCEPTIONS;

/**
 * The {@code JsonMask} class provides a mechanism for masking JSON data according to a set of filter rules.
 * Filters are defined as pairs of JSON paths and maskers, where each masker is responsible for masking data
 * that matches the corresponding JSON path.
 * <p>
 * The class uses the {@code com.jayway.jsonpath} library for parsing and modifying JSON data, and allows for
 * the use of custom maskers by implementing the {@code Masker} interface.
 *
 * @author Vladimir Rudnev
 */
public class JsonMask {

    /**
     * List of filters used to mask JSON data.
     */
    private final List<Filter> filters;

    /**
     * Configuration used by the {@code com.jayway.jsonpath} library to parse JSON data.
     */
    private final Configuration jsonpathConfig = Configuration.builder().options(ALWAYS_RETURN_LIST, SUPPRESS_EXCEPTIONS).build();

    /**
     * Constructs a new {@code JsonMask} object with the specified collection of filters.
     *
     * @param filters the collection of filters to use for masking JSON data.
     * @throws IllegalArgumentException if the collection of filters is null or empty.
     */
    private JsonMask(Collection<Filter> filters) {
        assertNotEmpty(filters);
        this.filters = filters.stream().sorted(Comparator.comparingInt(o -> o.getMasker().getOrder())).collect(Collectors.toUnmodifiableList());
    }

    /**
     * Returns a new {@code Builder} object used to create new {@code JsonMask} instances.
     *
     * @return a new {@code Builder} object.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Masks the specified JSON input according to the configured filters and returns the masked result.
     *
     * @param input the JSON data to be masked.
     * @return the masked JSON data.
     */
    public String mask(String input) {
        DocumentContext jsonContext = JsonPath.parse(input, jsonpathConfig);
        filters.forEach(filter -> applyFilters(jsonContext, filter));
        return jsonContext.jsonString();
    }

    /**
     * Applies the specified filter to the given JSON context.
     *
     * @param jsonContext the JSON context to apply the filter to.
     * @param filter      the filter to apply.
     */
    private void applyFilters(DocumentContext jsonContext, Filter filter) {
        if (REMOVE_MASKER.equals(filter.getMasker())) {
            jsonContext.delete(filter.getJsonPath());
            return;
        }
        jsonContext.map(filter.getJsonPath(), (currentValue, configuration) -> applyMasker(currentValue, filter.getMasker()));
    }

    /**
     * Masks a value using a given masker.
     *
     * @param input  the value to mask
     * @param masker the masker to use
     * @param <T>    the type of the value to mask
     * @return the masked value
     */
    private <T> Object applyMasker(Object input, Masker<T> masker) {
        if (masker.getJsonFieldType().isAssignableFrom(input.getClass())) {
            //noinspection unchecked
            return masker.mask((T) input);
        } else {
            return input;
        }
    }

    /**
     * The class provides a Builder to allow for fluent filter creation,
     * and filters can be added individually or in bulk.
     */
    public static class Builder {
        private final Map<String, Masker<?>> maskers = new HashMap<>();
        private final List<Filter> filters = new ArrayList<>();
        private final ArrayList<Pair<String, FiltersBuilder>> filtersBuilders = new ArrayList<>();

        private Builder() {
        }

        /**
         * Adds a new Masker to the builder.
         *
         * @param masker the Masker to be added.
         * @return The Builder instance, for chaining calls.
         */
        public Builder masker(Masker<?> masker) {
            assertNotNull(masker);
            this.maskers.put(masker.getName(), masker);
            return this;
        }

        /**
         * Adds a new Filter to the builder.
         *
         * @param filter the Filter to be added.
         * @return The Builder instance, for chaining calls.
         */
        public Builder filter(Filter filter) {
            assertNotNull(filter);
            masker(filter.getMasker());
            this.filters.add(filter);
            return this;
        }

        /**
         * Adds a collection of Filters to the builder.
         *
         * @param filters the collection of Filters to be added.
         * @return The Builder instance, for chaining calls.
         */
        public Builder filter(Collection<Filter> filters) {
            assertNotEmpty(filters);
            filters.forEach(this::filter);
            return this;
        }

        /**
         * Adds a single filter to the Builder, using a Masker and JSON path.
         *
         * @param masker   The Masker to use for the filter.
         * @param jsonPath The JSON path to apply the filter to.
         * @return The Builder instance, for chaining calls.
         */
        public Builder filter(Masker<?> masker, String jsonPath) {
            return filter(Filter.builder().masker(masker).jsonPath(jsonPath).build());
        }

        /**
         * Adds a single filter or a collection of Filters to the Builder, using a Masker and a FiltersBuilder function.
         *
         * @param masker          The Masker to use for the filter.
         * @param builderFunction A UnaryOperator that takes a new FiltersBuilder instance.
         * @return The Builder instance, for chaining calls.
         */
        public Builder filter(Masker<?> masker, UnaryOperator<FiltersBuilder> builderFunction) {
            assertNotNull(masker);
            return filter(builderFunction.apply(new FiltersBuilder()).masker(masker).build());
        }

        /**
         * Adds a single filter or a collection of Filters to the Builder,
         * using a masker name and a FiltersBuilder function.
         *
         * @param maskerName      The name of the Masker to use for the filter.
         * @param builderFunction A UnaryOperator that takes a new FiltersBuilder instance.
         * @return The Builder instance, for chaining calls.
         */
        public Builder filter(String maskerName, UnaryOperator<FiltersBuilder> builderFunction) {
            assertNotEmpty(maskerName);
            filtersBuilders.add(new Pair<>(maskerName, builderFunction.apply(new FiltersBuilder())));
            return this;
        }

        /**
         * Adds a single filter to the Builder, using a masker name and JSON path.
         *
         * @param maskerName The name of the Masker to use for the filter.
         * @param jsonPath   The JSON path to apply the filter to.
         * @return The Builder instance, for chaining calls.
         */
        public Builder filter(String maskerName, String jsonPath) {
            assertNotEmpty(maskerName);
            assertNotEmpty(jsonPath);
            filtersBuilders.add(new Pair<>(maskerName, new FiltersBuilder().jsonPath(jsonPath)));
            return this;
        }

        /**
         * Builds the JsonMask object based on the filters that have been added.
         *
         * @return A new JsonMask instance.
         */
        public JsonMask build() {
            filters.addAll(filtersBuilders.stream().map(p -> p.getRight().masker(maskers.get(p.getLeft())).build()).flatMap(Collection::stream).collect(Collectors.toList()));
            return new JsonMask(filters);
        }

        /**
         * A builder class used to construct Filters instances.
         */
        public static class FiltersBuilder {
            private final List<String> jsonPaths = new ArrayList<>();
            private Masker<?> masker;

            private FiltersBuilder() {
            }

            /**
             * Sets the masker to be used for the filters.
             *
             * @param masker the masker to use
             * @return the builder instance
             */
            private FiltersBuilder masker(Masker<?> masker) {
                assertNotNull(masker);
                this.masker = masker;
                return this;
            }

            /**
             * Adds a JSON path to the list of paths to be filtered.
             *
             * @param jsonPath the JSON path to add
             * @return the builder instance
             */
            public FiltersBuilder jsonPath(String jsonPath) {
                assertNotEmpty(jsonPath);
                this.jsonPaths.add(jsonPath);
                return this;
            }

            /**
             * Adds multiple JSON paths to the list of paths to be filtered.
             *
             * @param jsonPaths the collection of JSON paths to add
             * @return the builder instance
             */
            public FiltersBuilder jsonPath(Collection<String> jsonPaths) {
                assertNotEmpty(jsonPaths);
                jsonPaths.forEach(this::jsonPath);
                return this;
            }

            /**
             * Builds the list of {@link Filter} objects using the configured masker and JSON paths.
             *
             * @return a list of Filter objects
             */
            private List<Filter> build() {
                assertNotNull(this.masker);
                return jsonPaths.stream().map(jsonPath -> Filter.builder().masker(masker).jsonPath(jsonPath).build()).collect(Collectors.toList());
            }
        }

        /**
         * A simple utility class for holding a pair of values.
         */
        private static class Pair<L, R> {
            private final L left;
            private final R right;

            private Pair(L left, R right) {
                this.left = left;
                this.right = right;
            }

            private L getLeft() {
                return left;
            }

            private R getRight() {
                return right;
            }
        }

    }
}
