package com.butreik.dmask.core;

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
public interface JsonMask {

    /**
     * Masks the specified JSON input according to the configured filters and returns the masked result.
     *
     * @param input the JSON data to be masked.
     * @return the masked JSON data.
     */
    String mask(String input);
}
