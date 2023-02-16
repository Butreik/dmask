package com.butreik.dmask.core.maskers;

/**
 * This abstract class provides an implementation for the Masker interface
 * for masking string values. It implements the getJsonFieldType() method which
 * returns the class object representing the String class.
 *
 * @author Vladimir Rudnev
 */
public abstract class AbstractStringMasker implements Masker<String> {

    public Class<String> getJsonFieldType() {
        return String.class;
    }
}
