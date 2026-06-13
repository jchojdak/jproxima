package com.jchojdak.jproxima.impl.data.stats;

import com.jchojdak.jproxima.data.ColumnStats;

abstract class BaseColumnStats implements ColumnStats {

    private boolean countsComputed = false;
    private long cachedCount;
    private long cachedNullCount;

    protected abstract int size();

    protected abstract boolean isNullAt(int i);

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
}
