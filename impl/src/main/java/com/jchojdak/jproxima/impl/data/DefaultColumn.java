package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.Column;
import com.jchojdak.jproxima.data.DataType;

import java.util.Arrays;

/**
 * Default implementation of {@link Column}
 *
 * @see Column
 */
class DefaultColumn implements Column {

    private final String name;
    private final Object[] data;
    private final DataType type;

    DefaultColumn(String name, Object[] data, DataType type) {
        this.name = name;
        this.data = Arrays.copyOf(data, data.length);
        this.type = type;
    }

    @Override
    public Object get(int index) {
        return data[index];
    }

    @Override
    public int size() {
        return data.length;
    }

    @Override
    public DataType getType() {
        return type;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, data.length);
    }

    @Override
    public String getName() {
        return name;
    }
}