package com.jchojdak.jproxima.data;

/**
 * Specialized column for boolean values with zero-boxing access.
 * <p>
 * Instances of {@code BooleanColumn} are <b>immutable</b>.
 * Once created, the data in a column cannot be changed.
 *
 * @see Column
 */
public interface BooleanColumn extends Column {

    /**
     * Returns the boolean value at the specified row index.
     *
     * @param index row index
     * @return the value at the specified index
     */
    boolean getBoolean(int index);

    /**
     * Returns a copy of the column data as a primitive boolean array.
     * <p>
     * Modifying the returned array does not affect this column.
     *
     * @return a new boolean array containing all values from this column
     */
    boolean[] toBooleanArray();
}