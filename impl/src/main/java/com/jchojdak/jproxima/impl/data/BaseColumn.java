package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.Column;
import com.jchojdak.jproxima.data.DataType;

import java.util.BitSet;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Base implementation of {@link Column} providing common functionality for all column types
 *
 * @see Column
 * @see DefaultIntColumn
 * @see DefaultDoubleColumn
 * @see DefaultBooleanColumn
 * @see DefaultStringColumn
 */
abstract sealed class BaseColumn implements Column
        permits DefaultIntColumn, DefaultDoubleColumn, DefaultBooleanColumn, DefaultStringColumn {

    protected static final int DEFAULT_DISPLAY_LIMIT = 10;

    protected final String name;
    protected final DataType type;
    protected final int size;
    protected final BitSet nullMask;

    private int cachedHashCode;

    protected BaseColumn(String name, DataType type, int size) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.nullMask = new BitSet(size);
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
    public final Column rename(String newName) {
        if (name.equals(newName)) {
            return this;
        }

        return copyWithName(newName);
    }

    @Override
    public final String toString() {
        return toString(DEFAULT_DISPLAY_LIMIT);
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Column other)) {
            return false;
        }

        if (this.size != other.size() ||
                this.type != other.getType() ||
                !this.name.equals(other.getName())) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            boolean thisNull = this.isNull(i);
            boolean thatNull = other.isNull(i);

            if (thisNull != thatNull) return false;
            if (thisNull) continue;

            if (!Objects.equals(this.get(i), other.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public final int hashCode() {
        int hash = cachedHashCode;
        if (hash == 0 && size > 0) {
            hash = Objects.hash(name, type.name(), size);

            for (int i = 0; i < size; i++) {
                Object val = this.get(i);
                hash = 31 * hash + (val == null ? 0 : val.hashCode());
            }

            cachedHashCode = hash;
        }
        return hash;
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

    @Override
    public final boolean isNull(int index) {
        return nullMask.get(index);
    }

    protected abstract String valueToString(int index);

    protected abstract Column copyWithName(String newName);
}