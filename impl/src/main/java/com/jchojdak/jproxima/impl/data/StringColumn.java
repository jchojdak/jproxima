package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.DataType;

import java.util.Arrays;
import java.util.BitSet;

final class StringColumn extends BaseColumn {

    private final String[] data;

    StringColumn(String name, String[] data, BitSet nullMask) {
        super(name, DataType.STRING, data.length, nullMask);
        this.data = data;
    }

    @Override
    public Object get(int index) {
        return isNull(index) ? null : data[index];
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, data.length);
    }

    @Override
    protected String valueToString(int index) {
        return isNull(index) ? "null" : data[index];
    }
}
