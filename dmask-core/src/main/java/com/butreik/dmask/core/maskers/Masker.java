package com.butreik.dmask.core.maskers;

public interface Masker<T> {

    char REPLACEMENT_CHAR = '*';

    String getName();

    int getOrder();

    Class<T> getJsonFieldType();

    T mask(T input);

}
