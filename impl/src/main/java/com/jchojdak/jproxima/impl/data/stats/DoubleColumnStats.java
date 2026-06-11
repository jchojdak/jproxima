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
    public double min() {
        double min = Double.POSITIVE_INFINITY;
        boolean found = false;

        for (int i = 0; i < column.size(); i++) {
            if (column.isNull(i)) continue;

            min = Math.min(min, column.getDouble(i));
            found = true;
        }

        if (!found) {
            throw new IllegalStateException("Column is empty");
        }

        return min;
    }

    @Override
    public double max() {
        double max = Double.NEGATIVE_INFINITY;
        boolean found = false;

        for (int i = 0; i < column.size(); i++) {
            if (column.isNull(i)) continue;

            max = Math.max(max, column.getDouble(i));
            found = true;
        }

        if (!found) {
            throw new IllegalStateException("Column is empty");
        }

        return max;
    }

    @Override
    public double avg() {
        double sum = 0;
        long count = 0;

        for (int i = 0; i < column.size(); i++) {
            if (column.isNull(i)) continue;

            sum += column.getDouble(i);
            count++;
        }

        if (count == 0) {
            throw new IllegalStateException("Column is empty");
        }

        return sum / count;
    }

    @Override
    public double sum() {
        double sum = 0;

        for (int i = 0; i < column.size(); i++) {
            if (!column.isNull(i)) {
                sum += column.getDouble(i);
            }
        }

        return sum;
    }
}
