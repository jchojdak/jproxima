package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.Column;
import com.jchojdak.jproxima.data.DataFrame;

import java.util.LinkedHashMap;
import java.util.Map;

class DefaultDataFrame implements DataFrame {

    private final Map<String, Column> columns;

    DefaultDataFrame(Map<String, Column> columns) {
        this.columns = columns;
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
    public DataFrame getColumns(String... names) {
        Map<String, Column> selectedColumns = new LinkedHashMap<>();

        for (String name : names) {
            selectedColumns.put(name, columns.get(name));
        }

        return new DefaultDataFrame(selectedColumns);
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

            Column headColumn = new DefaultColumn(columnName, headData, originalColumn.getType());
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

            Column tailColumn = new DefaultColumn(columnName, tailData, originalColumn.getType());
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
}
