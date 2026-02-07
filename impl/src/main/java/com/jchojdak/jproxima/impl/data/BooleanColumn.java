package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.DataType;

final class BooleanColumn extends BaseColumn {

    private static final boolean DEFAULT_NULL_VALUE = false;

    private final boolean[] data;

    BooleanColumn(String name, Object[] data) {
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
