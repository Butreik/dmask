package com.butreik.dmask.core;

import com.butreik.dmask.core.maskers.Masker;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.butreik.dmask.core.maskers.Maskers.REMOVE_MASKER;
import static com.butreik.dmask.core.validate.Assert.assertNotEmpty;
import static com.jayway.jsonpath.Option.ALWAYS_RETURN_LIST;
import static com.jayway.jsonpath.Option.SUPPRESS_EXCEPTIONS;

public class JsonMask {

    private final List<Filter> filters;

    private final Configuration jsonpathConfig = Configuration.builder()
            .options(ALWAYS_RETURN_LIST, SUPPRESS_EXCEPTIONS)
            .build();

    public JsonMask(Collection<Filter> filters) {
        assertNotEmpty(filters);
        this.filters = filters.stream()
                .sorted(Comparator.comparingInt(o -> o.getMasker().getOrder()))
                .collect(Collectors.toUnmodifiableList());
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
}
