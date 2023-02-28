package com.butreik.dmask.core;

/**
 * The MapFunction interface provides a way to define a mapping function to apply to JSON values.
 *
 * @author Vladimir Rudnev
 */
@FunctionalInterface
public interface MapFunction {

    /**
     * The MapFunction interface provides a way to define a mapping function to apply to JSON values.
     *
     * @param input the input JSON value to map
     * @return the mapped JSON value
     */
    Object map(Object input);
}
