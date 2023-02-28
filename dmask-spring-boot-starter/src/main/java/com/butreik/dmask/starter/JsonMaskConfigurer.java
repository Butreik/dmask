package com.butreik.dmask.starter;


import com.butreik.dmask.core.JsonMaskImpl;

/**
 * This interface provides a method to configure the JSON masker using a builder.
 * Implement this interface to customize the JsonMaskImpl.Builder object.
 *
 * @author Vladimir Rudnev
 */
public interface JsonMaskConfigurer {

    /**
     * A functional interface for configuring a {@link com.butreik.dmask.core.JsonMaskImpl} builder.
     * Implementing classes should provide an implementation for this method to customize the builder.
     * @param builder The builder to be configured.
     */
    void configure(JsonMaskImpl.Builder builder);
}
