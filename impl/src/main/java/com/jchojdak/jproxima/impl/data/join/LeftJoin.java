package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.DataFrame;

import java.util.List;

/**
 * Left join implementation for DataFrames.
 * Returns all rows from the left DataFrame and matched rows from the right.
 */
final class LeftJoin extends BaseJoin {

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
            boolean matchedAny = false;
            for (int j = 0; j < rightRows; j++) {
                if (matches(left, i, right, j, leftKeys, rightKeys)) {
                    holder.addRow(left, i, right, j);
                    matchedAny = true;
                }
            }
            if (!matchedAny) {
                holder.addRow(left, i, right, null);
            }
        }

        return holder.build();
    }
}
