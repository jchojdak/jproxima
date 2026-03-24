package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.Column;
import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.impl.io.csv.CsvWriter;
import com.jchojdak.jproxima.impl.io.excel.ExcelWriter;

import java.util.*;

/**
 * Default implementation of {@link DataFrame}
 *
 * @see DataFrame
 */
class DefaultDataFrame implements DataFrame {

    private static final int DEFAULT_DISPLAY_LIMIT = 10;

    private final Map<String, Column> columns;

    DefaultDataFrame(Map<String, Column> columns) {
        this.columns = new LinkedHashMap<>(columns);
    }

    @Override
    public int rowCount() {
        if (columns.isEmpty()) {
            return 0;
        }
        return columns.values().iterator().next().size();
    }

    @Override
    public int columnCount() {
        return columns.size();
    }

    @Override
    public Column getColumn(String name) {
        return columns.get(name);
    }

    @Override
    public List<String> getColumnNames() {
        return new ArrayList<>(columns.keySet());
    }

    @Override
    public DataFrame getColumns(String... names) {
        Map<String, Column> selectedColumns = new LinkedHashMap<>();

        for (String name : names) {
            selectedColumns.put(name, columns.get(name));
        }

        return new DefaultDataFrame(selectedColumns);
    }

    @Override
    public DataFrame addColumn(Column column) {
        Map<String, Column> updatedColumns = new LinkedHashMap<>(columns);

        updatedColumns.put(column.getName(), column);

        return new DefaultDataFrame(updatedColumns);
    }

    @Override
    public DataFrame addColumn(String name, Column column) {
        Map<String, Column> updatedColumns = new LinkedHashMap<>(columns);

        updatedColumns.put(name, column);

        return new DefaultDataFrame(updatedColumns);
    }

    @Override
    public DataFrame dropColumn(String name) {
        Map<String, Column> updatedColumns = new LinkedHashMap<>(columns);

        updatedColumns.remove(name);

        return new DefaultDataFrame(updatedColumns);
    }

    @Override
    public DataFrame head(int n) {
        if (columns.isEmpty()) {
            return new DefaultDataFrame(new LinkedHashMap<>());
        }

        int actualRows = Math.min(n, rowCount());
        Map<String, Column> headColumns = new LinkedHashMap<>();

        for (Map.Entry<String, Column> entry : columns.entrySet()) {
            String columnName = entry.getKey();
            Column originalColumn = entry.getValue();

            Object[] headData = new Object[actualRows];
            for (int i = 0; i < actualRows; i++) {
                headData[i] = originalColumn.get(i);
            }

            Column headColumn = ColumnFactory.create(columnName, headData, originalColumn.getType());
            headColumns.put(columnName, headColumn);
        }

        return new DefaultDataFrame(headColumns);
    }

    @Override
    public DataFrame tail(int n) {
        if (columns.isEmpty()) {
            return new DefaultDataFrame(new LinkedHashMap<>());
        }

        int totalRows = rowCount();
        int actualRows = Math.min(n, rowCount());
        int startIndex = totalRows - actualRows;

        Map<String, Column> tailColumns = new LinkedHashMap<>();

        for (Map.Entry<String, Column> entry : columns.entrySet()) {
            String columnName = entry.getKey();
            Column originalColumn = entry.getValue();

            Object[] tailData = new Object[actualRows];
            for (int i = 0; i < actualRows; i++) {
                tailData[i] = originalColumn.get(startIndex + i);
            }

            Column tailColumn = ColumnFactory.create(columnName, tailData, originalColumn.getType());
            tailColumns.put(columnName, tailColumn);
        }

        return new DefaultDataFrame(tailColumns);
    }

    @Override
    public Object[][] toArray() {
        int totalRows = rowCount();
        int totalColumns = columnCount();

        Object[][] result = new Object[totalRows][totalColumns];

        int columnIndex = 0;
        for (Column col : columns.values()) {
            Object[] data = col.toArray();
            for (int i = 0; i < totalRows; i++) {
                result[i][columnIndex] = data[i];
            }
            columnIndex++;
        }

        return result;
    }

    @Override
    public String toString() {
        return toString(DEFAULT_DISPLAY_LIMIT);
    }

    @Override
    public String toString(int displayLimit) {
        if (columns.isEmpty()) {
            return "<Empty DataFrame>";
        }

        StringBuilder sb = new StringBuilder();
        int totalRows = rowCount();
        int rowsToShow = Math.min(displayLimit, totalRows);

        String[] columnNames = columns.keySet().toArray(new String[0]);
        int[] columnWidths = calculateColumnWidths(columnNames, rowsToShow);

        appendRow(sb, columnNames, columnWidths);
        sb.append("\n");

        for (int row = 0; row < rowsToShow; row++) {
            String[] rowData = new String[columnNames.length];
            for (int col = 0; col < columnNames.length; col++) {
                rowData[col] = String.valueOf(columns.get(columnNames[col]).get(row));
            }
            appendRow(sb, rowData, columnWidths);
            if (row < rowsToShow - 1 || totalRows > displayLimit) {
                sb.append("\n");
            }
        }

        if (totalRows > displayLimit) {
            sb.append("... (").append(totalRows - displayLimit).append(" more rows)");
        }

        return sb.toString();
    }

    @Override
    public void toCsv(String path) {
        CsvWriter.write(path, columns.values());
    }

    @Override
    public void toExcel(String path) {
        ExcelWriter.write(path, this);
    }

    private int[] calculateColumnWidths(String[] columnNames, int rowsToShow) {
        int[] widths = new int[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            widths[i] = columnNames[i].length();
            Column column = columns.get(columnNames[i]);
            for (int row = 0; row < rowsToShow; row++) {
                widths[i] = Math.max(widths[i], String.valueOf(column.get(row)).length());
            }
        }
        return widths;
    }

    private void appendRow(StringBuilder sb, String[] values, int[] widths) {
        for (int i = 0; i < values.length; i++) {
            sb.append(String.format("%-" + widths[i] + "s", values[i]));
            if (i < values.length - 1) {
                sb.append("  ");
            }
        }
    }
}
