package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.join.JoinType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DataFrameJoiner {

    private static final Map<JoinType, JoinStrategy> STRATEGIES = new HashMap<>();

    static {
        register(JoinType.INNER, new InnerJoin());
        register(JoinType.LEFT, new LeftJoin());
        register(JoinType.RIGHT, new RightJoin());
        register(JoinType.FULL, new FullJoin());
    }

    private DataFrameJoiner() {
    }

    static void register(JoinType type, JoinStrategy strategy) {
        STRATEGIES.put(type, strategy);
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
        JoinStrategy strategy = STRATEGIES.get(type);

        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported join type: " + type);
        }

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
