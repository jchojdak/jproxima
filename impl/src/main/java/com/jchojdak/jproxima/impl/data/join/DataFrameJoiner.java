package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.join.JoinType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Entry point for performing JOIN operations on DataFrames.
 * Delegates execution to a specific JoinStrategy implementation.
 */
public final class DataFrameJoiner {

    private static final Map<JoinType, JoinStrategy> STRATEGIES = new EnumMap<>(JoinType.class);

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
        validateKeys(leftKeys, rightKeys);

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

    /**
     * Validates join key lists.
     */
    private static void validateKeys(List<String> leftKeys, List<String> rightKeys) {
        if (leftKeys.isEmpty() || rightKeys.isEmpty()) {
            throw new IllegalArgumentException("Join keys must not be empty");
        }

        if (leftKeys.size() != rightKeys.size()) {
            throw new IllegalArgumentException(
                    "Left and right key lists must have the same size, got: "
                            + leftKeys.size() + " vs " + rightKeys.size()
            );
        }
    }
}
