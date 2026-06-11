package com.jchojdak.jproxima.impl.data.stats;

import com.jchojdak.jproxima.data.ColumnStats;

abstract class BaseColumnStats implements ColumnStats {

    protected abstract int size();
    protected abstract boolean isNullAt(int i);

    @Override
    public long count() {
        long c = 0;

        for (int i = 0; i < size(); i++) {
            if (!isNullAt(i)) c++;
        }

        return c;
    }

    @Override
    public long nullCount() {
        long c = 0;

        for (int i = 0; i < size(); i++) {
            if (isNullAt(i)) {
                c++;
            }
        }

        return c;
    }
}
