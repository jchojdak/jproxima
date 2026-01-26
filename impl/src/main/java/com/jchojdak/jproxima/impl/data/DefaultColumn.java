package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.Column;
import com.jchojdak.jproxima.data.DataType;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link Column}
 *
 * @see Column
 */
class DefaultColumn implements Column {

    private static final int DEFAULT_DISPLAY_LIMIT = 10;

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

    @Override
    public String toString() {
        return toString(DEFAULT_DISPLAY_LIMIT);
    }

    @Override
    public String toString(int displayLimit) {
        int totalRows = size();
        int endLimit = Math.min(displayLimit, totalRows);

        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" [").append(type).append("]: ");

        String content = Arrays.stream(data, 0, endLimit)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        sb.append(content);

        if (totalRows > displayLimit)
            sb.append(", ...");

        return sb.toString();
    }
}