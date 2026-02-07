package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.DataType;

final class DoubleColumn extends BaseColumn {

    private static final double DEFAULT_NULL_VALUE = 0.0;

    private final double[] data;

    DoubleColumn(String name, Object[] data) {
        super(name, DataType.DOUBLE, data.length);
        this.data = new double[size];

        for (int i = 0; i < size; i++) {
            if (data[i] == null) {
                nullMask.set(i);
                this.data[i] = DEFAULT_NULL_VALUE;
            } else {
                this.data[i] = ((Number) data[i]).doubleValue();
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
