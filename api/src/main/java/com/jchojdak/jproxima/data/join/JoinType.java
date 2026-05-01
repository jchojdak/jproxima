package com.jchojdak.jproxima.data.join;

/**
 * Defines the type of join operation for combining two DataFrames.
 */
public enum JoinType {

    /**
     * Inner join.
     * <p>
     * Returns only rows where join keys match in both DataFrames.
     */
    INNER,

    /**
     * Left outer join.
     * <p>
     * Returns all rows from the left DataFrame and matched rows from the right.
     * Non-matching rows on the right side are filled with {@code null}.
     */
    LEFT,

    /**
     * Right outer join.
     * <p>
     * Returns all rows from the right DataFrame and matched rows from the left.
     * Non-matching rows on the left side are filled with {@code null}.
     */
    RIGHT,

    /**
     * Full outer join.
     * <p>
     * Returns all rows from both DataFrames.
     * Non-matching sides are filled with {@code null}.
     */
    FULL
}