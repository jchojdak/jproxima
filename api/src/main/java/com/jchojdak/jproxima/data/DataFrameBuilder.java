package com.jchojdak.jproxima.data;

import java.util.ServiceLoader;

/**
 * Builder for creating {@link DataFrame} instances.
 *
 * @see DataFrame
 */
public interface DataFrameBuilder {

    /**
     * Adds a column to the DataFrame being built.
     * <p>
     * All columns must have the same number of elements (rows).
     *
     * @param name the name of the column
     * @param data the data for the column
     * @return {@code this} builder instance for method chaining
     */
    DataFrameBuilder addColumn(String name, Object[] data);

    /**
     * Adds a column to the DataFrame being built.
     * <p>
     * All columns must have the same number of elements (rows).
     *
     * @param name the name of the column
     * @param data the data for the column
     * @param type the type of the column
     * @return {@code this} builder instance for method chaining
     */
    DataFrameBuilder addColumn(String name, Object[] data, DataType type);

    /**
     * Adds a column to the DataFrame being built.
     * <p>
     * All columns must have the same number of elements (rows).
     *
     * @param column {@link Column} instance
     * @return {@code this} builder instance for method chaining
     *
     * @see Column
     * @see ColumnBuilder
     */
    DataFrameBuilder addColumn(Column column);

    /**
     * Builds and returns the final immutable {@link DataFrame}
     * containing all columns that have been added to the builder.
     *
     * @return immutable {@link DataFrame} instance
     */
    DataFrame build();

    /**
     * Creates a new {@link DataFrameBuilder} instance using
     * the {@link ServiceLoader} mechanism.
     *
     * @return new builder instance
     * @throws IllegalStateException if no implementation is found
     */
    static DataFrameBuilder create() {
        ServiceLoader<DataFrameBuilder> loader = ServiceLoader.load(DataFrameBuilder.class);
        return loader.findFirst()
                .orElseThrow(() -> new IllegalStateException("No DataFrameBuilder implementation found"));
    }
}
