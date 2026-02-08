package com.jchojdak.jproxima.data;

import java.util.ServiceLoader;

/**
 * Builder for creating {@link IntColumn} instances with zero-boxing overhead.
 *
 * @see Column
 * @see IntColumn
 */
public interface IntColumnBuilder {

    /**
     * Sets the name of the column.
     *
     * @param name the column name
     * @return {@code this} builder instance for method chaining
     */
    IntColumnBuilder name(String name);

    /**
     * Adds a single int value to the column.
     *
     * @param value the value to add
     * @return {@code this} builder instance for method chaining
     */
    IntColumnBuilder add(int value);

    /**
     * Adds multiple int values.
     *
     * @param values values to add
     * @return {@code this} builder instance for method chaining
     */
    IntColumnBuilder add(int[] values);

    /**
     * Adds a null value.
     *
     * @return {@code this} builder instance for method chaining
     */
    IntColumnBuilder addNull();

    /**
     * Builds and returns the final immutable {@link IntColumn}
     *
     * @return immutable {@link IntColumn} instance
     */
    IntColumn build();

    /**
     * Creates a new {@link IntColumnBuilder} instance using
     * the {@link ServiceLoader} mechanism.
     *
     * @return new builder
     * @throws IllegalStateException if no implementation is found
     */
    static IntColumnBuilder init() {
        ServiceLoader<IntColumnBuilder> loader = ServiceLoader.load(IntColumnBuilder.class);
        return loader.findFirst()
                .orElseThrow(() -> new IllegalStateException("No IntColumnBuilder implementation found"));
    }
}