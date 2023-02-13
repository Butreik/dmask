package com.butreik.dmask.core.maskers;

public final class RemoveMasker implements Masker<Object> {

    public static RemoveMasker INSTANCE = new RemoveMasker();

    private RemoveMasker() {
    }

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
