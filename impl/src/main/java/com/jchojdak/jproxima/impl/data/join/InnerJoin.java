package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.*;

import java.util.*;

/**
 * Inner join implementation for DataFrames.
 * Returns only matching rows from both sides.
 */
final class InnerJoin extends BaseJoin {

    @Override
    public DataFrame join(
            DataFrame left,
            DataFrame right,
            List<String> leftKeys,
            List<String> rightKeys,
            String leftSuffix,
            String rightSuffix
    ) {
        JoinResultHolder holder = new JoinResultHolder(left, right, rightSuffix);

        int leftRows = left.getColumn(left.getColumnNames().getFirst()).size();
        int rightRows = right.getColumn(right.getColumnNames().getFirst()).size();

        for (int i = 0; i < leftRows; i++) {
            for (int j = 0; j < rightRows; j++) {
                if (matches(left, i, right, j, leftKeys, rightKeys)) {
                    holder.addRow(left, i, right, j);
                }
            }
        }

        return holder.build();
    }
}
