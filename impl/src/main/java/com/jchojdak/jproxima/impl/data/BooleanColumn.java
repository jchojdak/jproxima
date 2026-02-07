package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.DataType;

import java.util.BitSet;

final class BooleanColumn extends BaseColumn {

    private final boolean[] data;

    BooleanColumn(String name, boolean[] data, BitSet nullMask) {
        super(name, DataType.BOOLEAN, data.length, nullMask);
        this.data = data;
    }

    @Override
    public Object get(int index) {
        return isNull(index) ? null : data[index];
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = isNull(i) ? data[i] : null;
        }
        return result;
    }

    @Override
    protected String valueToString(int index) {
        return isNull(index) ? "null" : String.valueOf(data[index]);
    }
}
