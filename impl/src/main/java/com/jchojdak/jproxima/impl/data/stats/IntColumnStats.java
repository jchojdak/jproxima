package com.jchojdak.jproxima.impl.data.stats;

import com.jchojdak.jproxima.data.IntColumn;

public final class IntColumnStats extends BaseColumnStats {

    private final IntColumn column;

    private boolean aggregatesComputed = false;
    private int cachedMin;
    private int cachedMax;
    private long cachedSum;

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

    private synchronized void computeAggregates() {
        if (aggregatesComputed) return;

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        long sum = 0;

        int n = column.size();
        for (int i = 0; i < n; i++) {
            if (column.isNull(i)) continue;
            int v = column.getInt(i);

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
        return (double) cachedSum / c;
    }
}
