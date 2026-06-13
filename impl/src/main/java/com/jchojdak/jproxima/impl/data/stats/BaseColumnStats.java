package com.jchojdak.jproxima.impl.data.stats;

import com.jchojdak.jproxima.data.ColumnStats;

abstract class BaseColumnStats implements ColumnStats {

    private boolean countsComputed = false;
    private long cachedCount;
    private long cachedNullCount;

    private boolean aggregatesComputed = false;
    private double cachedMin;
    private double cachedMax;
    private double cachedSum;

    protected abstract int size();

    protected abstract boolean isNullAt(int i);

    protected abstract double valueAt(int i);

    protected abstract double initialMin();

    protected abstract double initialMax();

    private synchronized void computeCounts() {
        if (countsComputed) return;
        long nulls = 0;
        int n = size();
        for (int i = 0; i < n; i++) {
            if (isNullAt(i)) nulls++;
        }
        cachedNullCount = nulls;
        cachedCount = n - nulls;
        countsComputed = true;
    }

    @Override
    public long count() {
        computeCounts();
        return cachedCount;
    }

    @Override
    public long nullCount() {
        computeCounts();
        return cachedNullCount;
    }

    private synchronized void computeAggregates() {
        if (aggregatesComputed) return;
        double min = initialMin();
        double max = initialMax();
        double sum = 0;
        int n = size();
        for (int i = 0; i < n; i++) {
            if (isNullAt(i)) continue;
            double v = valueAt(i);
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
        if (count() == 0) throw new IllegalStateException("Column is empty");
        return cachedMin;
    }

    @Override
    public double max() {
        computeAggregates();
        if (count() == 0) throw new IllegalStateException("Column is empty");
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
        if (c == 0) throw new IllegalStateException("Column is empty");
        return cachedSum / c;
    }
}
