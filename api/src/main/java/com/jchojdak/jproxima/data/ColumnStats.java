package com.jchojdak.jproxima.data;

/**
 * Provides basic statistical operations for a numeric column in a {@link DataFrame}.
 *
 * <p>
 * This interface is intended to be used only with numeric columns such as
 * {@link com.jchojdak.jproxima.data.IntColumn} or
 * {@link com.jchojdak.jproxima.data.DoubleColumn}.
 * Attempting to use it with non-numeric columns will result in
 * {@link UnsupportedOperationException}.
 *
 * <h2>Example usage</h2>
 * <pre>
 * DataFrame df = ...
 *
 * Column age = df.getColumn("age");
 *
 * ColumnStats stats = age.stats();
 *
 * double min = stats.min();
 * double min2 = df.getColumn("age").stats().min(); // alternative way to get min
 *
 * double max = stats.max();
 * double max2 = df.getColumn("age").stats().max(); // alternative way to get max
 *
 * double avg = stats.avg();
 * long count = stats.count();
 * </pre>
 *
 * <p>
 * All operations ignore {@code null} values.
 */
public interface ColumnStats {

    /**
     * Returns the minimum non-null value in the column.
     *
     * @return minimum value
     */
    double min();

    /**
     * Returns the maximum non-null value in the column.
     *
     * @return maximum value
     */
    double max();

    /**
     * Returns the arithmetic mean of all non-null values.
     *
     * @return average value
     */
    double avg();

    /**
     * Returns the sum of all non-null values.
     *
     * @return sum of values
     */
    double sum();

    /**
     * Returns the number of non-null values in the column.
     *
     * @return non-null value count
     */
    long count();

    /**
     * Returns the number of null values in the column.
     *
     * @return null value count
     */
    long nullCount();
}
