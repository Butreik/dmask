package com.butreik.dmask.core.maskers;

public abstract class AbstractStringMasker implements Masker<String> {

    public Class<String> getJsonFieldType() {
        return String.class;
    }
}
