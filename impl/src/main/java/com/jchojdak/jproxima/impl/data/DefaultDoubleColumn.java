package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.ColumnStats;
import com.jchojdak.jproxima.data.DataType;
import com.jchojdak.jproxima.data.DoubleColumn;
import com.jchojdak.jproxima.impl.data.stats.DoubleColumnStats;

import java.util.Arrays;
import java.util.BitSet;

/**
 * Default implementation of {@link DoubleColumn}.
 */
final class DefaultDoubleColumn extends BaseColumn implements DoubleColumn {

    private static final double DEFAULT_NULL_VALUE = 0.0;

    private final double[] data;
    private volatile ColumnStats stats;

    DefaultDoubleColumn(String name, double[] data, BitSet nullMask) {
        super(name, DataType.DOUBLE, data.length);
        this.data = data;
        if (nullMask != null) {
            this.nullMask.or(nullMask);
        }
    }

    DefaultDoubleColumn(String name, Object[] data) {
        super(name, DataType.DOUBLE, data.length);
        this.data = new double[size];

        for (int i = 0; i < size; i++) {
            if (data[i] == null) {
                nullMask.set(i);
                this.data[i] = DEFAULT_NULL_VALUE;
            } else {
                this.data[i] = ((Number) data[i]).doubleValue();
            }
        }
    }

    @Override
    public double getDouble(int index) {
        if (isNull(index)) {
            throw new NullPointerException("Null value at index " + index);
        }
        return data[index];
    }

    @Override
    public double[] toDoubleArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    public Object get(int index) {
        return isNull(index) ? null : data[index];
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = isNull(i) ? null : data[i];
        }
        return result;
    }

    @Override
    protected String valueToString(int index) {
        return isNull(index) ? "null" : String.valueOf(data[index]);
    }

    @Override
    public ColumnStats stats() {
        ColumnStats s = stats;

        if (s == null) {
            synchronized (this) {
                s = stats;

                if (s == null) {
                    s = new DoubleColumnStats(this);
                    stats = s;
                }
            }
        }

        return s;
    }
}