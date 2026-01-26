package com.jchojdak.jproxima.data;

/**
 * Represents a 2-dimensional table of data.
 * <p>
 * Instances of {@code DataFrame} are <b>immutable</b>.
 * All modification methods return a new {@code DataFrame}.
 */
public interface DataFrame {

    /**
     * Returns the number of rows.
     *
     * @return row count
     */
    int rowCount();

    /**
     * Returns the number of columns.
     *
     * @return column count
     */
    int columnCount();

    /**
     * Returns a column by name.
     *
     * @param name column name
     * @return column
     */
    Column getColumn(String name);

    /**
     * Returns a new DataFrame with only the selected columns.
     *
     * @param names names of columns to select
     * @return new DataFrame with selected columns
     */
    DataFrame getColumns(String... names);

    /**
     * Returns a new DataFrame with an added column.
     *
     * @param column column to add
     * @return new DataFrame with added column
     */
    DataFrame addColumn(Column column);

    /**
     * Returns a new DataFrame with an added column.
     *
     * @param name   column name
     * @param column column to add
     * @return new DataFrame with added column
     */
    DataFrame addColumn(String name, Column column);

    /**
     * Returns a new DataFrame without the specified column.
     *
     * @param name column name to remove
     * @return new DataFrame without the column
     */
    DataFrame dropColumn(String name);

    /**
     * Returns the first {@code n} rows as a new DataFrame.
     *
     * @param n number of rows to include
     * @return new DataFrame with first {@code n} rows
     */
    DataFrame head(int n);

    /**
     * Returns the last {@code n} rows as a new DataFrame.
     *
     * @param n number of rows to include
     * @return new DataFrame with last {@code n} rows
     */
    DataFrame tail(int n);

    /**
     * Returns the data as a 2D array.
     *
     * @return 2D array of all data
     */
    Object[][] toArray();

    /**
     * Returns a string representation of the DataFrame, with up to {@code displayLimit} rows shown.
     *
     * @param displayLimit max number of rows to show
     * @return string representation of the DataFrame
     */
    String toString(int displayLimit);
}
