package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.IntColumn;
import com.jchojdak.jproxima.data.IntColumnBuilder;

import java.util.Arrays;
import java.util.BitSet;

public final class DefaultIntColumnBuilder implements IntColumnBuilder {

    private static final int INITIAL_CAPACITY = 1024;
    private static final int DEFAULT_NULL_VALUE = 0;

    private String name;
    private int size;
    private int[] data;
    private BitSet nullMask;

    public DefaultIntColumnBuilder() {
        this.data = new int[INITIAL_CAPACITY];
        this.size = 0;
        this.nullMask = null;
    }

    @Override
    public IntColumnBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IntColumnBuilder add(int value) {
        ensureCapacity(size + 1);
        data[size++] = value;
        return this;
    }

    @Override
    public IntColumnBuilder add(int[] values) {
        if (values == null || values.length == 0) {
            return this;
        }
        ensureCapacity(size + values.length);
        System.arraycopy(values, 0, data, size, values.length);
        size += values.length;
        return this;
    }

    @Override
    public IntColumnBuilder addNull() {
        ensureCapacity(size + 1);
        if (nullMask == null) {
            nullMask = new BitSet();
        }
        nullMask.set(size);
        data[size++] = DEFAULT_NULL_VALUE;
        return this;
    }

    @Override
    public IntColumn build() {
        int[] trimmedData = size == data.length ? data : Arrays.copyOf(data, size);
        return new DefaultIntColumn(name, trimmedData, nullMask);
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