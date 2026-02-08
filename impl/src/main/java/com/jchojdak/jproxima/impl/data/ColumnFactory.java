package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.*;

final class ColumnFactory {

    static Column create(String name, Object[] data, DataType type) {
        return switch (type) {
            case INTEGER -> new DefaultIntColumn(name, data);
            case DOUBLE -> new DefaultDoubleColumn(name, data);
            case BOOLEAN -> new DefaultBooleanColumn(name, data);
            case STRING -> new DefaultStringColumn(name, data);
            default -> throw new IllegalArgumentException("Unsupported data type: " + type);
        };
    }
}