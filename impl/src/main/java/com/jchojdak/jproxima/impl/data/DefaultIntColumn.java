package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.DataType;
import com.jchojdak.jproxima.data.IntColumn;

import java.util.Arrays;
import java.util.BitSet;

final class DefaultIntColumn extends BaseColumn implements IntColumn {

    private static final int DEFAULT_NULL_VALUE = 0;

    private final int[] data;

    DefaultIntColumn(String name, int[] data, BitSet nullMask) {
        super(name, DataType.INTEGER, data.length);
        this.data = data;
        if (nullMask != null) {
            this.nullMask.or(nullMask);
        }
    }

    DefaultIntColumn(String name, Object[] data) {
        super(name, DataType.INTEGER, data.length);
        this.data = new int[size];

        for (int i = 0; i < size; i++) {
            if (data[i] == null) {
                nullMask.set(i);
                this.data[i] = DEFAULT_NULL_VALUE;
            } else {
                this.data[i] = ((Number) data[i]).intValue();
            }
        }
    }

    @Override
    public int getInt(int index) {
        if (isNull(index)) {
            throw new NullPointerException("Null value at index " + index);
        }
        return data[index];
    }

    @Override
    public int[] toIntArray() {
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
}