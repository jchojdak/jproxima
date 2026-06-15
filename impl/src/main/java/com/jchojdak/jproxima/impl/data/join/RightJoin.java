package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.DataFrame;

import java.util.List;

/**
 * Right join implementation for DataFrames.
 * Implemented by delegating to reversed left join.
 */
final class RightJoin extends BaseJoin {

    @Override
    public DataFrame join(
            DataFrame left,
            DataFrame right,
            List<String> leftKeys,
            List<String> rightKeys,
            String leftSuffix,
            String rightSuffix
    ) {
        return new LeftJoin().join(
                right,
                left,
                rightKeys,
                leftKeys,
                rightSuffix,
                leftSuffix
        );
    }
}
