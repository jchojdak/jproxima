package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.DataFrame;

import java.util.List;

/**
 * Strategy interface for DataFrame join operations.
 */
public interface JoinStrategy {

    /**
     * Executes a join between two DataFrames using given keys.
     */
    DataFrame join(
            DataFrame left,
            DataFrame right,
            List<String> leftKeys,
            List<String> rightKeys,
            String leftSuffix,
            String rightSuffix
    );
}
