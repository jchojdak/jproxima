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
     * Checks if the value at the specified index is null.
     *
     * @param index row index
     * @return {@code true} if the value is null, {@code false} otherwise
     */
    boolean isNull(int index);

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
     * Returns a new column with name changed to {@code newName}.
     *
     * @param newName new column name
     * @return new instance of column
     */
    Column rename(String newName);

    /**
     * Returns a string representation of the column, with up to {@code displayLimit} values shown.
     *
     * @param displayLimit max number of elements to show
     * @return string representation of the column
     */
    String toString(int displayLimit);

    /**
     * Returns statistics for this column if it is numeric.
     *
     * <p>
     * Only numeric column implementations support this operation.
     *
     * @return column statistics
     * @throws UnsupportedOperationException if column is not numeric
     */
    default ColumnStats stats() {
        throw new UnsupportedOperationException("Stats supported only for numeric columns");
    }
}
