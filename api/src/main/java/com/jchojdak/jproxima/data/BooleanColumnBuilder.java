package com.jchojdak.jproxima.data;

import java.util.ServiceLoader;

/**
 * Builder for creating {@link BooleanColumn} instances with zero-boxing overhead.
 *
 * @see Column
 * @see BooleanColumn
 */
public interface BooleanColumnBuilder {

    /**
     * Sets the name of the column.
     *
     * @param name the column name
     * @return {@code this} builder instance for method chaining
     */
    BooleanColumnBuilder name(String name);

    /**
     * Adds a single boolean value to the column.
     *
     * @param value the value to add
     * @return {@code this} builder instance for method chaining
     */
    BooleanColumnBuilder add(boolean value);

    /**
     * Adds multiple boolean values.
     *
     * @param values values to add
     * @return {@code this} builder instance for method chaining
     */
    BooleanColumnBuilder add(boolean[] values);

    /**
     * Adds a null value.
     *
     * @return {@code this} builder instance for method chaining
     */
    BooleanColumnBuilder addNull();

    /**
     * Builds and returns the final immutable {@link BooleanColumn}.
     *
     * @return immutable {@link BooleanColumn} instance
     */
    BooleanColumn build();

    /**
     * Creates a new {@link BooleanColumnBuilder} instance using
     * the {@link ServiceLoader} mechanism.
     *
     * @return new builder
     * @throws IllegalStateException if no implementation is found
     */
    static BooleanColumnBuilder init() {
        ServiceLoader<BooleanColumnBuilder> loader = ServiceLoader.load(BooleanColumnBuilder.class);
        return loader.findFirst()
                .orElseThrow(() -> new IllegalStateException("No BooleanColumnBuilder implementation found"));
    }
}