package com.jchojdak.jproxima.data;

import java.util.ServiceLoader;

/**
 * Builder for creating {@link StringColumn} instances.
 *
 * @see Column
 * @see StringColumn
 */
public interface StringColumnBuilder {

    /**
     * Sets the name of the column.
     *
     * @param name the column name
     * @return {@code this} builder instance for method chaining
     */
    StringColumnBuilder name(String name);

    /**
     * Adds a single String value to the column (null allowed).
     *
     * @param value the value to add
     * @return {@code this} builder instance for method chaining
     */
    StringColumnBuilder add(String value);

    /**
     * Adds multiple String values.
     *
     * @param values values to add
     * @return {@code this} builder instance for method chaining
     */
    StringColumnBuilder add(String[] values);

    /**
     * Builds and returns the final immutable {@link StringColumn}.
     *
     * @return immutable {@link StringColumn} instance
     */
    StringColumn build();

    /**
     * Creates a new {@link StringColumnBuilder} instance using
     * the {@link ServiceLoader} mechanism.
     *
     * @return new builder
     * @throws IllegalStateException if no implementation is found
     */
    static StringColumnBuilder init() {
        ServiceLoader<StringColumnBuilder> loader = ServiceLoader.load(StringColumnBuilder.class);
        return loader.findFirst()
                .orElseThrow(() -> new IllegalStateException("No StringColumnBuilder implementation found"));
    }
}