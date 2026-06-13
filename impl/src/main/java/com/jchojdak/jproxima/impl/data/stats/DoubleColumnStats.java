package com.jchojdak.jproxima.impl.data.stats;

import com.jchojdak.jproxima.data.DoubleColumn;

public final class DoubleColumnStats extends BaseColumnStats {

    private final DoubleColumn column;

    private boolean aggregatesComputed = false;
    private double cachedMin;
    private double cachedMax;
    private double cachedSum;

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

    private synchronized void computeAggregates() {
        if (aggregatesComputed) return;

        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        double sum = 0;

        int n = column.size();
        for (int i = 0; i < n; i++) {
            if (column.isNull(i)) continue;
            double v = column.getDouble(i);

            if (v < min) min = v;
            if (v > max) max = v;
            sum += v;
        }

        cachedMin = min;
        cachedMax = max;
        cachedSum = sum;
        aggregatesComputed = true;
    }

    @Override
    public double min() {
        computeAggregates();
        if (count() == 0)
            throw new IllegalStateException("Column is empty");
        return cachedMin;
    }

    @Override
    public double max() {
        computeAggregates();
        if (count() == 0)
            throw new IllegalStateException("Column is empty");
        return cachedMax;
    }

    @Override
    public double sum() {
        computeAggregates();
        return cachedSum;
    }

    @Override
    public double avg() {
        computeAggregates();
        long c = count();
        if (c == 0)
            throw new IllegalStateException("Column is empty");
        return cachedSum / c;
    }
}
