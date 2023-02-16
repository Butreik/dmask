package com.butreik.dmask.core.maskers;

/**
 * Implementation of Masker that removes the data.
 *
 * @author Vladimir Rudnev
 */
public final class RemoveMasker implements Masker<Object> {

    /**
     * The singleton instance of the RemoveMasker.
     */
    public static RemoveMasker INSTANCE = new RemoveMasker();

    private RemoveMasker() {
    }

    /**
     * Returns null as the input data is to be removed.
     *
     * @param input The input data.
     * @return The null.
     */
    @Override
    public Object mask(Object input) {
        return null;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    @Override
    public Class<Object> getJsonFieldType() {
        return Object.class;
    }
}
