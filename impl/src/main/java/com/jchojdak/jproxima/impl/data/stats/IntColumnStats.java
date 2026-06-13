package com.jchojdak.jproxima.impl.data.stats;

import com.jchojdak.jproxima.data.IntColumn;

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
    protected boolean isNullAt(int i) {
        return column.isNull(i);
    }

    @Override
    protected double valueAt(int i) {
        return column.getInt(i);
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