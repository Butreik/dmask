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

import static com.butreik.dmask.core.maskers.Maskers.REMOVE_MASKER;
import static com.butreik.dmask.core.validate.Assert.assertNotEmpty;
import static com.butreik.dmask.core.validate.Assert.assertNotNull;
import static com.jayway.jsonpath.Option.ALWAYS_RETURN_LIST;
import static com.jayway.jsonpath.Option.SUPPRESS_EXCEPTIONS;

public class JsonMask {

    private final List<Filter> filters;

    private final Configuration jsonpathConfig = Configuration.builder()
            .options(ALWAYS_RETURN_LIST, SUPPRESS_EXCEPTIONS)
            .build();

    private JsonMask(Collection<Filter> filters) {
        assertNotEmpty(filters);
        this.filters = filters.stream()
                .sorted(Comparator.comparingInt(o -> o.getMasker().getOrder()))
                .collect(Collectors.toUnmodifiableList());
    }

    public static Builder builder() {
        return new Builder();
    }

    public String mask(String input) {
        DocumentContext jsonContext = JsonPath.parse(input, jsonpathConfig);
        filters.forEach(filter -> applyFilters(jsonContext, filter));
        return jsonContext.jsonString();
    }

    private void applyFilters(DocumentContext jsonContext, Filter filter) {
        if (REMOVE_MASKER.equals(filter.getMasker())) {
            jsonContext.delete(filter.getJsonPath());
            return;
        }
        jsonContext.map(
                filter.getJsonPath(),
                (currentValue, configuration) -> mask(currentValue, filter.getMasker()));
    }

    private <T> Object mask(Object input, Masker<T> masker) {
        if (masker.getJsonFieldType().isAssignableFrom(input.getClass())) {
            //noinspection unchecked
            return masker.mask((T) input);
        } else {
            return input;
        }
    }

    public static class Builder {
        private final Map<String, Masker<?>> maskers = new HashMap<>();
        private final List<Filter> filters = new ArrayList<>();
        private final ArrayList<Pair<String, FiltersBuilder>> filtersBuilders = new ArrayList<>();

        private Builder() {
        }

        public Builder masker(Masker<?> masker) {
            assertNotNull(masker);
            this.maskers.put(masker.getName(), masker);
            return this;
        }

        public Builder filter(Filter filter) {
            assertNotNull(filter);
            masker(filter.getMasker());
            this.filters.add(filter);
            return this;
        }

        public Builder filter(Collection<Filter> filters) {
            assertNotEmpty(filters);
            filters.forEach(this::filter);
            return this;
        }

        public Builder filter(Masker<?> masker, String jsonPath) {
            return filter(Filter.builder().masker(masker).jsonPath(jsonPath).build());
        }

        public Builder filter(Masker<?> masker, UnaryOperator<FiltersBuilder> builderFunction) {
            assertNotNull(masker);
            return filter(builderFunction.apply(new FiltersBuilder()).masker(masker).build());
        }

        public Builder filter(String maskerName, UnaryOperator<FiltersBuilder> builderFunction) {
            assertNotEmpty(maskerName);
            filtersBuilders.add(new Pair<>(maskerName, builderFunction.apply(new FiltersBuilder())));
            return this;
        }

        public Builder filter(String maskerName, String jsonPath) {
            assertNotEmpty(maskerName);
            assertNotEmpty(jsonPath);
            filtersBuilders.add(new Pair<>(maskerName, new FiltersBuilder().jsonPath(jsonPath)));
            return this;
        }

        public JsonMask build() {
            filters.addAll(filtersBuilders.stream()
                    .map(p -> p.getRight().masker(maskers.get(p.getLeft())).build())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList()));
            return new JsonMask(filters);
        }

        public static class FiltersBuilder {
            private final List<String> jsonPaths = new ArrayList<>();
            private Masker<?> masker;

            private FiltersBuilder() {
            }

            private FiltersBuilder masker(Masker<?> masker) {
                assertNotNull(masker);
                this.masker = masker;
                return this;
            }

            public FiltersBuilder jsonPath(String jsonPath) {
                assertNotEmpty(jsonPath);
                this.jsonPaths.add(jsonPath);
                return this;
            }

            public FiltersBuilder jsonPath(Collection<String> jsonPaths) {
                assertNotEmpty(jsonPaths);
                jsonPaths.forEach(this::jsonPath);
                return this;
            }

            private List<Filter> build() {
                assertNotNull(this.masker);
                return jsonPaths.stream()
                        .map(jsonPath -> Filter.builder().masker(masker).jsonPath(jsonPath).build())
                        .collect(Collectors.toList());
            }
        }

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
