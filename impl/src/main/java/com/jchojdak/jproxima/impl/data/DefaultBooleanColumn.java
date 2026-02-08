package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.BooleanColumn;
import com.jchojdak.jproxima.data.DataType;

import java.util.Arrays;
import java.util.BitSet;

final class DefaultBooleanColumn extends BaseColumn implements BooleanColumn {

    private static final boolean DEFAULT_NULL_VALUE = false;

    private final boolean[] data;

    DefaultBooleanColumn(String name, boolean[] data, BitSet nullMask) {
        super(name, DataType.BOOLEAN, data.length);
        this.data = data;
        if (nullMask != null) {
            this.nullMask.or(nullMask);
        }
    }

    DefaultBooleanColumn(String name, Object[] data) {
        super(name, DataType.BOOLEAN, data.length);
        this.data = new boolean[size];

        for (int i = 0; i < size; i++) {
            if (data[i] == null) {
                nullMask.set(i);
                this.data[i] = DEFAULT_NULL_VALUE;
            } else {
                this.data[i] = (Boolean) data[i];
            }
        }
    }

    @Override
    public boolean getBoolean(int index) {
        if (isNull(index)) {
            throw new NullPointerException("Null value at index " + index);
        }
        return data[index];
    }

    @Override
    public boolean[] toBooleanArray() {
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