package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.StringColumn;
import com.jchojdak.jproxima.data.StringColumnBuilder;

import java.util.Arrays;

/**
 * Builder for {@link StringColumn}.
 * Nulls are handled naturally in the String array.
 */
public final class DefaultStringColumnBuilder extends BaseColumnBuilder implements StringColumnBuilder {

    private static final int INITIAL_CAPACITY = 1024;

    private String name;
    private int size;
    private String[] data;

    public DefaultStringColumnBuilder() {
        this.data = new String[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public StringColumnBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public StringColumnBuilder add(String value) {
        ensureCapacity(size + 1);
        data[size++] = value;
        return this;
    }

    @Override
    public StringColumnBuilder add(String[] values) {
        if (isNullOrEmpty(values)) {
            return this;
        }
        ensureCapacity(size + values.length);
        System.arraycopy(values, 0, data, size, values.length);
        size += values.length;
        return this;
    }

    @Override
    public StringColumn build() {
        String[] trimmedData = size == data.length ? data : Arrays.copyOf(data, size);

        Object[] objectData = new Object[trimmedData.length];
        System.arraycopy(trimmedData, 0, objectData, 0, trimmedData.length);

        return new DefaultStringColumn(name, objectData);
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