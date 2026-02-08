package com.jchojdak.jproxima.data;

/**
 * Specialized column for string values
 */
public interface StringColumn extends Column {

    String getString(int index);

    String[] toStringArray();
}