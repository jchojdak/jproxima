package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.DoubleColumn;
import com.jchojdak.jproxima.data.DoubleColumnBuilder;

import java.util.Arrays;
import java.util.BitSet;

/**
 * Memory-efficient builder for {@link DoubleColumn}.
 * Uses primitive double array to avoid boxing overhead.
 */
public final class DefaultDoubleColumnBuilder implements DoubleColumnBuilder {

    private static final int INITIAL_CAPACITY = 1024;
    private static final double DEFAULT_NULL_VALUE = 0.0;

    private String name;
    private int size;
    private double[] data;
    private BitSet nullMask;

    public DefaultDoubleColumnBuilder() {
        this.data = new double[INITIAL_CAPACITY];
        this.size = 0;
        this.nullMask = null;
    }

    @Override
    public DoubleColumnBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public DoubleColumnBuilder add(double value) {
        ensureCapacity(size + 1);
        data[size++] = value;
        return this;
    }

    @Override
    public DoubleColumnBuilder add(double[] values) {
        if (values == null || values.length == 0) {
            return this;
        }
        ensureCapacity(size + values.length);
        System.arraycopy(values, 0, data, size, values.length);
        size += values.length;
        return this;
    }

    @Override
    public DoubleColumnBuilder addNull() {
        ensureCapacity(size + 1);
        if (nullMask == null) {
            nullMask = new BitSet();
        }
        nullMask.set(size);
        data[size++] = DEFAULT_NULL_VALUE;
        return this;
    }

    @Override
    public DoubleColumn build() {
        double[] trimmedData = size == data.length ? data : Arrays.copyOf(data, size);
        return new DefaultDoubleColumn(name, trimmedData, nullMask);
    }

    /**
     * Ensures the internal array can hold at least the specified capacity.
     * Growth strategy: 1.5x for memory efficiency.
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = Math.max(minCapacity, data.length + (data.length >> 1));
            data = Arrays.copyOf(data, newCapacity);
        }
    }
}