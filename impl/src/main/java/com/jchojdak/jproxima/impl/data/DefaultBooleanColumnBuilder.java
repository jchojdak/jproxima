package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.BooleanColumn;
import com.jchojdak.jproxima.data.BooleanColumnBuilder;

import java.util.Arrays;
import java.util.BitSet;

/**
 * Memory-efficient builder for {@link BooleanColumn}.
 * Uses primitive boolean array.
 */
public final class DefaultBooleanColumnBuilder extends BaseColumnBuilder implements BooleanColumnBuilder {

    private static final int INITIAL_CAPACITY = 10;
    private static final boolean DEFAULT_NULL_VALUE = false;

    private String name;
    private int size;
    private boolean[] data;
    private BitSet nullMask;

    public DefaultBooleanColumnBuilder() {
        this.data = new boolean[INITIAL_CAPACITY];
        this.size = 0;
        this.nullMask = null;
    }

    @Override
    public BooleanColumnBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public BooleanColumnBuilder add(boolean value) {
        ensureCapacity(size + 1);
        data[size++] = value;
        return this;
    }

    @Override
    public BooleanColumnBuilder add(boolean[] values) {
        if (isNullOrEmpty(values)) {
            return this;
        }
        ensureCapacity(size + values.length);
        System.arraycopy(values, 0, data, size, values.length);
        size += values.length;
        return this;
    }

    @Override
    public BooleanColumnBuilder addNull() {
        ensureCapacity(size + 1);
        if (nullMask == null) {
            nullMask = new BitSet();
        }
        nullMask.set(size);
        data[size++] = DEFAULT_NULL_VALUE;
        return this;
    }

    @Override
    public BooleanColumn build() {
        boolean[] trimmedData = size == data.length ? data : Arrays.copyOf(data, size);
        return new DefaultBooleanColumn(name, trimmedData, nullMask);
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
