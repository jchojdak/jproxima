package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.JoinType;

import java.util.List;

public final class DataFrameJoiner {

    private static final JoinStrategy INNER = new InnerJoin();
    private static final JoinStrategy LEFT  = new LeftJoin();
    private static final JoinStrategy RIGHT = new RightJoin();
    private static final JoinStrategy FULL  = new FullJoin();

    private DataFrameJoiner() {
    }

    public static DataFrame join(
            DataFrame left,
            DataFrame right,
            JoinType type,
            List<String> leftKeys,
            List<String> rightKeys,
            String leftSuffix,
            String rightSuffix
    ) {
        JoinStrategy strategy = switch (type) {
            case INNER -> INNER;
            case LEFT -> LEFT;
            case RIGHT -> RIGHT;
            case FULL -> FULL;
        };

        return strategy.join(
                left,
                right,
                leftKeys,
                rightKeys,
                leftSuffix,
                rightSuffix
        );
    }
}
