package com.jchojdak.jproxima.data;

import java.util.ServiceLoader;

/**
 * Builder for creating {@link Column} instances.
 * <p>
 * <b>This API is deprecated.</b> Use specialized builders for better performance:
 * <ul>
 *   <li>{@link IntColumnBuilder} for integer columns</li>
 *   <li>{@link DoubleColumnBuilder} for double columns</li>
 *   <li>{@link BooleanColumnBuilder} for boolean columns</li>
 *   <li>{@link StringColumnBuilder} for string columns</li>
 * </ul>
 *
 * @deprecated Use type-specific builders ({@link IntColumnBuilder}, {@link DoubleColumnBuilder},
 *             {@link BooleanColumnBuilder}, {@link StringColumnBuilder}) for zero-boxing performance
 *             and better memory efficiency.
 * @see IntColumnBuilder
 * @see DoubleColumnBuilder
 * @see BooleanColumnBuilder
 * @see StringColumnBuilder
 * @see Column
 */
@Deprecated(since = "0.2.0", forRemoval = true)
public interface ColumnBuilder {

    /**
     * Sets the name of the column.
     *
     * @param name the column name
     * @return {@code this} builder instance for method chaining
     * @deprecated Use specialized builders instead
     */
    @Deprecated(since = "0.2.0")
    ColumnBuilder name(String name);

    /**
     * Sets the data type of the column.
     *
     * @param type the data type
     * @return {@code this} builder instance for method chaining
     * @deprecated Use specialized builders instead
     */
    @Deprecated(since = "0.2.0")
    ColumnBuilder type(DataType type);

    /**
     * Adds a single value to the column.
     *
     * @param value the value to add
     * @return {@code this} builder instance for method chaining
     * @deprecated Use specialized builders instead
     */
    @Deprecated(since = "0.2.0")
    ColumnBuilder add(Object value);

    /**
     * Adds multiple values to the column from an array.
     * <p>
     * Each value is added in the order provided.
     *
     * @param values one or more values to add
     * @return {@code this} builder instance for method chaining
     * @deprecated Use specialized builders instead
     */
    @Deprecated(since = "0.2.0")
    ColumnBuilder addAll(Object[] values);

    /**
     * Adds multiple values to the column from an {@link Iterable}.
     * <p>
     * Each value is added in the order provided.
     *
     * @param values an {@link Iterable} of values to add
     * @return {@code this} builder instance for method chaining
     * @deprecated Use specialized builders instead
     */
    @Deprecated(since = "0.2.0")
    ColumnBuilder addAll(Iterable<?> values);

    /**
     * Builds and returns the final immutable {@link Column}
     * containing all columns that have been added to the builder.
     *
     * @return immutable {@link Column} instance
     * @throws IllegalStateException if required fields are not set
     * @deprecated Use specialized builders instead
     */
    @Deprecated(since = "0.2.0")
    Column build();

    /**
     * Creates a new {@link ColumnBuilder} instance using
     * the {@link ServiceLoader} mechanism.
     *
     * @return new builder instance
     * @throws IllegalStateException if no implementation is found
     * @deprecated Use specialized builders instead
     */
    @Deprecated(since = "0.2.0")
    static ColumnBuilder create() {
        ServiceLoader<ColumnBuilder> loader = ServiceLoader.load(ColumnBuilder.class);
        return loader.findFirst()
                .orElseThrow(() -> new IllegalStateException("No ColumnBuilder implementation found"));
    }
}
