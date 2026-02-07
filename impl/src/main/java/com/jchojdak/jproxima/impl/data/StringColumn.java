package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.DataType;

import java.util.Arrays;

final class StringColumn extends BaseColumn {

    private static final String DEFAULT_NULL_VALUE = null;

    private final String[] data;

    public StringColumn(String name, Object[] data) {
        super(name, DataType.INTEGER, data.length);
        this.data = new String[size];

        for (int i = 0; i < size; i++) {
            if (data[i] == null) {
                nullMask.set(i);
                this.data[i] = DEFAULT_NULL_VALUE;
            } else {
                this.data[i] = data[i].toString();
            }
        }
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
