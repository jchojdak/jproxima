package com.jchojdak.jproxima.data;

/**
 * Represents a column in a {@link DataFrame}.
 * <p>
 * Instances of {@code Column} are <b>immutable</b>.
 * Once created, the data in a column cannot be changed.
 */
public interface Column {

    /**
     * Returns the value at the specified row index.
     *
     * @param index row index
     * @return the value at the specified index
     */
    Object get(int index);

    /**
     * Returns the number of rows in this column.
     *
     * @return number of rows
     */
    int size();


    /**
     * Returns the type of this column.
     *
     * @return column type, never {@code null}
     */
    DataType getType();

    /**
     * Returns a copy of the column data as an array.
     * <p>
     * Modifying the returned array does not affect this column.
     *
     * @return a new array containing all values from this column
     */
    Object[] toArray();

    /**
     * Returns the name of this column.
     *
     * @return column name
     */
    String getName();

    /**
     * Returns a string representation of the column, with up to {@code displayLimit} values shown.
     *
     * @param displayLimit max number of elements to show
     * @return string representation of the column
     */
    String toString(int displayLimit);
}
