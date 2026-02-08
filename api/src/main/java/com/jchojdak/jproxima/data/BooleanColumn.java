package com.jchojdak.jproxima.data;

/**
 * Specialized column for boolean values with zero-boxing access.
 */
public interface BooleanColumn extends Column {

    boolean getBoolean(int index);

    boolean[] toBooleanArray();
}