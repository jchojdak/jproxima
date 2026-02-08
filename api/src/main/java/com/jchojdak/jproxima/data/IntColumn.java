package com.jchojdak.jproxima.data;

/**
 * Specialized column for integer values with zero-boxing access.
 * <p>
 * Instances of {@code IntColumn} are <b>immutable</b>.
 * Once created, the data in a column cannot be changed.
 *
 * @see Column
 */
public interface IntColumn extends Column {

    /**
     * Returns the integer value at the specified row index.
     *
     * @param index row index
     * @return the value at the specified index
     */
    int getInt(int index);

    /**
     * Returns a copy of the column data as a primitive int array.
     * <p>
     * Modifying the returned array does not affect this column.
     *
     * @return a new int array containing all values from this column
     */
    int[] toIntArray();
}