package com.jchojdak.jproxima.impl.data.stats;

import com.jchojdak.jproxima.data.ColumnStats;
import com.jchojdak.jproxima.data.IntColumn;

public final class IntColumnStats implements ColumnStats {

    private final IntColumn column;

    public IntColumnStats(IntColumn column) {
        this.column = column;
    }

    @Override
    public double min() {
        int min = Integer.MAX_VALUE;
        boolean found = false;

        for (int i = 0; i < column.size(); i++) {
            if (column.isNull(i)) continue;

            min = Math.min(min, column.getInt(i));
            found = true;
        }

        if (!found) {
            throw new IllegalStateException("Column is empty");
        }

        return min;
    }

    @Override
    public double max() {
        int max = Integer.MIN_VALUE;
        boolean found = false;

        for (int i = 0; i < column.size(); i++) {
            if (column.isNull(i)) continue;

            max = Math.max(max, column.getInt(i));
            found = true;
        }

        if (!found) {
            throw new IllegalStateException("Column is empty");
        }

        return max;
    }

    @Override
    public double avg() {
        long sum = 0;
        long count = 0;

        for (int i = 0; i < column.size(); i++) {
            if (column.isNull(i)) continue;

            sum += column.getInt(i);
            count++;
        }

        if (count == 0) {
            throw new IllegalStateException("Column is empty");
        }

        return (double) sum / count;
    }

    @Override
    public double sum() {
        long sum = 0;

        for (int i = 0; i < column.size(); i++) {
            if (!column.isNull(i)) {
                sum += column.getInt(i);
            }
        }

        return sum;
    }

    @Override
    public long count() {
        long c = 0;

        for (int i = 0; i < column.size(); i++) {
            if (!column.isNull(i)) c++;
        }

        return c;
    }

    @Override
    public long nullCount() {
        return column.size() - count();
    }
}
