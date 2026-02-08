package com.jchojdak.jproxima.data;

/**
 * Specialized column for integer values with zero-boxing access.
 */
public interface IntColumn extends Column {

    int getInt(int index);

    int[] toIntArray();
}