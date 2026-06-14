package com.jchojdak.jproxima.impl.data.stats;

import com.jchojdak.jproxima.data.DoubleColumn;

/**
 * Concrete implementation of BaseColumnStats for double-based columns.
 * Adapts DoubleColumn data access.
 */
public final class DoubleColumnStats extends BaseColumnStats {

    private final DoubleColumn column;

    public DoubleColumnStats(DoubleColumn column) {
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
        return column.getDouble(index);
    }

    @Override
    protected double initialMin() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    protected double initialMax() {
        return Double.NEGATIVE_INFINITY;
    }
}