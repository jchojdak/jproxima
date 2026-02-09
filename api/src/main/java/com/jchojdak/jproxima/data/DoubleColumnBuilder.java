package com.jchojdak.jproxima.data;

import java.util.ServiceLoader;

/**
 * Builder for creating {@link DoubleColumn} instances with zero-boxing overhead.
 *
 * @see Column
 * @see DoubleColumn
 */
public interface DoubleColumnBuilder {

    /**
     * Sets the name of the column.
     *
     * @param name the column name
     * @return {@code this} builder instance for method chaining
     */
    DoubleColumnBuilder name(String name);

    /**
     * Adds a single double value to the column.
     *
     * @param value the value to add
     * @return {@code this} builder instance for method chaining
     */
    DoubleColumnBuilder add(double value);

    /**
     * Adds multiple double values.
     *
     * @param values values to add
     * @return {@code this} builder instance for method chaining
     */
    DoubleColumnBuilder add(double[] values);

    /**
     * Adds a null value.
     *
     * @return {@code this} builder instance for method chaining
     */
    DoubleColumnBuilder addNull();

    /**
     * Builds and returns the final immutable {@link DoubleColumn}.
     *
     * @return immutable {@link DoubleColumn} instance
     */
    DoubleColumn build();

    /**
     * Creates a new {@link DoubleColumnBuilder} instance using
     * the {@link ServiceLoader} mechanism.
     *
     * @return new builder
     * @throws IllegalStateException if no implementation is found
     */
    static DoubleColumnBuilder init() {
        ServiceLoader<DoubleColumnBuilder> loader = ServiceLoader.load(DoubleColumnBuilder.class);
        return loader.findFirst()
                .orElseThrow(() -> new IllegalStateException("No DoubleColumnBuilder implementation found"));
    }
}