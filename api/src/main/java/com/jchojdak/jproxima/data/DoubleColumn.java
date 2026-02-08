package com.jchojdak.jproxima.data;

/**
 * Specialized column for double values with zero-boxing access.
 * <p>
 * Instances of {@code DoubleColumn} are <b>immutable</b>.
 * Once created, the data in a column cannot be changed.
 *
 * @see Column
 */
public interface DoubleColumn extends Column {

    /**
     * Returns the double value at the specified row index.
     *
     * @param index row index
     * @return the value at the specified index
     */
    double getDouble(int index);

    /**
     * Returns a copy of the column data as a primitive double array.
     * <p>
     * Modifying the returned array does not affect this column.
     *
     * @return a new double array containing all values from this column
     */
    double[] toDoubleArray();
}