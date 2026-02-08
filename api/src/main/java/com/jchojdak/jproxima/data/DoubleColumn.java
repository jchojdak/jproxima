package com.jchojdak.jproxima.data;

/**
 * Specialized column for double values with zero-boxing access.
 */
public interface DoubleColumn extends Column {

    double getDouble(int index);

    double[] toDoubleArray();
}