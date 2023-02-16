package com.butreik.dmask.core.maskers;

/**
 * @author Vladimir Rudnev
 */
public interface Masker<T> {

    /**
     * The default character used for replacing sensitive data.
     */
    char DEFAULT_REPLACEMENT_CHAR = '*';

    /**
     * Returns the name of the masker.
     *
     * @return the name of the masker
     */
    String getName();

    /**
     * Returns the order of the masker, which determines the order in which multiple
     * maskers are applied to the same input.
     *
     * @return the order of the masker
     */
    int getOrder();

    /**
     * Returns the class of the data that the masker can mask.
     *
     * @return the class of the data that the masker can mask
     */
    Class<T> getJsonFieldType();

    /**
     * Masks the sensitive data in the input and returns the masked data.
     *
     * @param input the sensitive data to be masked
     * @return the masked data
     */
    T mask(T input);

}
