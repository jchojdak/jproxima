package com.jchojdak.jproxima.data;

/**
 * Specialized column for string values.
 * <p>
 * Instances of {@code StringColumn} are <b>immutable</b>.
 * Once created, the data in a column cannot be changed.
 *
 * @see Column
 */
public interface StringColumn extends Column {

    /**
     * Returns the String value at the specified row index.
     *
     * @param index row index
     * @return the value at the specified index
     */
    String getString(int index);

    /**
     * Returns a copy of the column data as a String array.
     * <p>
     * Modifying the returned array does not affect this column.
     *
     * @return a new String array containing all values from this column
     */
    String[] toStringArray();
}