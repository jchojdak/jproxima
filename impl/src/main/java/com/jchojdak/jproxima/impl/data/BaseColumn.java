package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.Column;
import com.jchojdak.jproxima.data.DataType;

import java.util.BitSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Base implementation of {@link Column}
 *
 * @see Column
 */
abstract sealed class BaseColumn implements Column
        permits IntColumn, DoubleColumn, BooleanColumn/*, StringColumn*/ {

    protected static final int DEFAULT_DISPLAY_LIMIT = 10;

    protected final String name;
    protected final DataType type;
    protected final int size;
    protected final BitSet nullMask;

    protected BaseColumn(String name, DataType type, int size, BitSet nullMask) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.nullMask = nullMask;
    }

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final DataType getType() {
        return type;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final String toString() {
        return toString(DEFAULT_DISPLAY_LIMIT);
    }

    @Override
    public final String toString(int displayLimit) {
        int endLimit = Math.min(displayLimit, size);

        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" [").append(type).append("]: ");

        String content = IntStream.range(0, endLimit)
                .mapToObj(this::valueToString)
                .collect(Collectors.joining(", "));
        sb.append(content);

        if (size > displayLimit)
            sb.append(", ...");

        return sb.toString();
    }

    protected final boolean isNull(int index) {
        return nullMask.get(index);
    }

    protected abstract String valueToString(int index);
}