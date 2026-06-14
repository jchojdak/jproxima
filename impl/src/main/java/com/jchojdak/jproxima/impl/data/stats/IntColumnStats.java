package com.jchojdak.jproxima.impl.data.stats;

import com.jchojdak.jproxima.data.IntColumn;

/**
 * Concrete implementation of BaseColumnStats for integer-based columns.
 * Adapts IntColumn data access.
 */
public final class IntColumnStats extends BaseColumnStats {

    private final IntColumn column;

    public IntColumnStats(IntColumn column) {
        this.column = column;
    }

    @Override
    protected int size() {
        return column.size();
    }

    @Override
    protected boolean isNullAt(int index) {
        return column.isNull(index);
    }

    @Override
    protected double valueAt(int index) {
        return column.getInt(index);
    }

    @Override
    protected double initialMin() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected double initialMax() {
        return Integer.MIN_VALUE;
    }
}