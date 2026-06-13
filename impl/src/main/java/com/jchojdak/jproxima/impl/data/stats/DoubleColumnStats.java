package com.jchojdak.jproxima.impl.data.stats;

import com.jchojdak.jproxima.data.DoubleColumn;

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
    protected boolean isNullAt(int i) {
        return column.isNull(i);
    }

    @Override
    protected double valueAt(int i) {
        return column.getDouble(i);
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