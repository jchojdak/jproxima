package com.jchojdak.jproxima.impl.data.stats;

import com.jchojdak.jproxima.data.ColumnStats;

/**
 * Base implementation of ColumnStats providing lazy evaluation and caching.
 * Computes counts and basic aggregates (min, max, sum) on-demand and caches
 * results for subsequent calls. Thread-safe via internal synchronization.
 */
abstract class BaseColumnStats implements ColumnStats {

    private boolean countsComputed = false;
    private long cachedCount;
    private long cachedNullCount;

    private boolean aggregatesComputed = false;
    private double cachedMin;
    private double cachedMax;
    private double cachedSum;

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

    @Override
    public double min() {
        computeAggregates();
        if (count() == 0) {
            throw new IllegalStateException("Column is empty");
        }
        return cachedMin;
    }

    @Override
    public double max() {
        computeAggregates();
        if (count() == 0) {
            throw new IllegalStateException("Column is empty");
        }
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
        long totalCount = count();
        if (totalCount == 0) {
            throw new IllegalStateException("Column is empty");
        }
        return cachedSum / totalCount;
    }

    protected abstract int size();

    protected abstract boolean isNullAt(int index);

    protected abstract double valueAt(int index);

    protected abstract double initialMin();

    protected abstract double initialMax();

    private synchronized void computeCounts() {
        if (countsComputed) {
            return;
        }

        long nulls = 0;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (isNullAt(i)) {
                nulls++;
            }
        }

        cachedNullCount = nulls;
        cachedCount = size - nulls;
        countsComputed = true;
    }

    private synchronized void computeAggregates() {
        if (aggregatesComputed) {
            return;
        }

        double min = initialMin();
        double max = initialMax();
        double sum = 0;
        int size = size();

        for (int i = 0; i < size; i++) {
            if (isNullAt(i)) {
                continue;
            }

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
}
