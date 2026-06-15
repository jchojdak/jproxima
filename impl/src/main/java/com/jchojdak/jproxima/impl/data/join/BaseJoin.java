package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.*;

import java.util.*;

/**
 * Base implementation of DataFrame join strategies.
 * Provides shared logic for result building and row matching.
 */
abstract class BaseJoin implements JoinStrategy {

    /**
     * Helper class for collecting and building join results.
     * Stores intermediate column data before creating final DataFrame.
     */
    protected static class JoinResultHolder {
        final Map<String, List<Object>> columns = new LinkedHashMap<>();
        final List<String> leftColumnNames;
        final List<String> rightColumnNames;
        final String rightSuffix;

        /**
         * Initializes result column structure for join operation.
         */
        JoinResultHolder(DataFrame left, DataFrame right, String rightSuffix) {
            this.leftColumnNames = left.getColumnNames();
            this.rightColumnNames = right.getColumnNames();
            this.rightSuffix = rightSuffix;

            for (String col : leftColumnNames) {
                columns.put(col, new ArrayList<>());
            }
            for (String col : rightColumnNames) {
                String finalName = columns.containsKey(col) ? col + rightSuffix : col;
                columns.put(finalName, new ArrayList<>());
            }
        }

        /**
         * Adds a joined row to the result, handling nulls for unmatched rows.
         */
        void addRow(DataFrame left, Integer leftRowIdx, DataFrame right, Integer rightRowIdx) {
            for (String col : leftColumnNames) {
                Object val = (leftRowIdx != null) ? left.getColumn(col).get(leftRowIdx) : null;
                columns.get(col).add(val);
            }

            for (String col : rightColumnNames) {
                String finalName = leftColumnNames.contains(col) ? col + rightSuffix : col;
                Object val = (rightRowIdx != null) ? right.getColumn(col).get(rightRowIdx) : null;
                columns.get(finalName).add(val);
            }
        }

        /**
         * Builds final DataFrame from collected join data.
         */
        DataFrame build() {
            DataFrameBuilder builder = DataFrameBuilder.create();
            for (Map.Entry<String, List<Object>> entry : columns.entrySet()) {
                String columnName = entry.getKey();
                Object[] data = entry.getValue().toArray();
                DataType type = (data.length == 0) ? DataType.STRING : DataType.from(data);
                builder.addColumn(columnName, data, type);
            }
            return builder.build();
        }
    }

    /**
     * Checks whether two rows from left and right DataFrames match
     * based on the provided join keys.
     */
    protected boolean matches(DataFrame left, int leftRow, DataFrame right, int rightRow, List<String> leftKeys, List<String> rightKeys) {
        for (int k = 0; k < leftKeys.size(); k++) {
            Object leftValue = left.getColumn(leftKeys.get(k)).get(leftRow);
            Object rightValue = right.getColumn(rightKeys.get(k)).get(rightRow);
            if (!Objects.equals(leftValue, rightValue)) {
                return false;
            }
        }
        return true;
    }
}