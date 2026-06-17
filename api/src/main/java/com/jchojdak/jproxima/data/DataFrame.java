package com.jchojdak.jproxima.data;

import java.util.List;

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
     * Returns a new DataFrame instance with a column renamed.
     *
     * @param oldName old column name
     * @param newName new column name
     * @return new DataFrame instance with the column renamed
     */
    DataFrame renameColumn(String oldName, String newName);

    /**
     * Returns a column by name.
     *
     * @param name column name
     * @return column
     */
    Column getColumn(String name);

    /**
     * Returns an ordered list of column names.
     *
     * @return list of column names in insertion order
     */
    List<String> getColumnNames();

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

    /**
     * Writes the DataFrame to a CSV file.
     *
     * @param path path to the output file
     */
    void toCsv(String path);

    /**
     * Writes the DataFrame to an Excel file (.xlsx).
     *
     * @param path path to the output file
     */
    void toXlsx(String path);

    /**
     * Writes the DataFrame to an Excel file (.xlsx).
     *
     * @param path path to the output file
     */
    void toExcel(String path);

    /**
     * Performs a join between this DataFrame (left) and another DataFrame (right).
     *
     * <p>This is the most general join method. All other overloads delegate to this one.</p>
     *
     * @param other       right DataFrame
     * @param type        join type
     * @param leftKeys    join keys from left DataFrame
     * @param rightKeys   join keys from right DataFrame
     * @param leftSuffix  suffix for conflicting left columns
     * @param rightSuffix suffix for conflicting right columns
     * @return new joined DataFrame
     */
    DataFrame join(
            DataFrame other,
            JoinType type,
            List<String> leftKeys,
            List<String> rightKeys,
            String leftSuffix,
            String rightSuffix
    );

    /**
     * Join using single key columns with custom suffixes.
     */
    default DataFrame join(
            DataFrame other,
            JoinType type,
            String leftKey,
            String rightKey,
            String leftSuffix,
            String rightSuffix
    ) {
        return join(
                other,
                type,
                List.of(leftKey),
                List.of(rightKey),
                leftSuffix,
                rightSuffix
        );
    }

    /**
     * Join using a single key column with default suffixes ({@code _x}, {@code _y}).
     */
    default DataFrame join(
            DataFrame other,
            JoinType type,
            String leftKey,
            String rightKey
    ) {
        return join(
                other,
                type,
                List.of(leftKey),
                List.of(rightKey),
                "_x",
                "_y"
        );
    }

    /**
     * Join using multiple key columns with default suffixes.
     */
    default DataFrame join(
            DataFrame other,
            JoinType type,
            List<String> leftKeys,
            List<String> rightKeys
    ) {
        return join(
                other,
                type,
                leftKeys,
                rightKeys,
                "_x",
                "_y"
        );
    }

    /**
     * Join using the same column name on both sides.
     */
    default DataFrame join(
            DataFrame other,
            JoinType type,
            String key
    ) {
        return join(
                other,
                type,
                List.of(key),
                List.of(key),
                "_x",
                "_y"
        );
    }

    /**
     * Join using multiple identical column names on both sides.
     */
    default DataFrame join(
            DataFrame other,
            JoinType type,
            List<String> keys
    ) {
        return join(
                other,
                type,
                keys,
                keys,
                "_x",
                "_y"
        );
    }
}
