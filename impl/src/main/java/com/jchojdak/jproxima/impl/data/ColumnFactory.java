package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.Column;
import com.jchojdak.jproxima.data.DataType;

class ColumnFactory {

    static Column create(String name, Object[] data, DataType type) {
        return switch (type) {
            case INTEGER -> new IntColumn(name, data);
            case DOUBLE -> new DoubleColumn(name, data);
            case BOOLEAN -> new BooleanColumn(name, data);
            case STRING -> new StringColumn(name, data);
            default -> throw new IllegalArgumentException("Unsupported data type: " + type);
        };
    }
}
