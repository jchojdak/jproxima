package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.DataFrame;

import java.util.List;

/**
 * Full outer join implementation for DataFrames.
 * Returns all rows from both sides, matched where possible.
 */
final class FullJoin extends BaseJoin {

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

        boolean[] rightRowMatched = new boolean[rightRows];

        for (int i = 0; i < leftRows; i++) {
            boolean matchedAny = false;
            for (int j = 0; j < rightRows; j++) {
                if (matches(left, i, right, j, leftKeys, rightKeys)) {
                    holder.addRow(left, i, right, j);
                    matchedAny = true;
                    rightRowMatched[j] = true;
                }
            }
            if (!matchedAny) {
                holder.addRow(left, i, right, null);
            }
        }

        for (int j = 0; j < rightRows; j++) {
            if (!rightRowMatched[j]) {
                holder.addRow(left, null, right, j);
            }
        }

        return holder.build();
    }
}
