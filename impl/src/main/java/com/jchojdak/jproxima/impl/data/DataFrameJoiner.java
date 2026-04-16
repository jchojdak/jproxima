package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.JoinType;

import java.util.List;

final class DataFrameJoiner {

    private DataFrameJoiner() {
    }

    static DataFrame join(
            DataFrame left,
            DataFrame right,
            JoinType type,
            List<String> leftKeys,
            List<String> rightKeys,
            String leftSuffix,
            String rightSuffix
    ) {
        // TODO: join logic

        return null;
    }
}
