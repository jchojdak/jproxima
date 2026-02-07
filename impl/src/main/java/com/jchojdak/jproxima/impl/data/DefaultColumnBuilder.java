package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.Column;
import com.jchojdak.jproxima.data.ColumnBuilder;
import com.jchojdak.jproxima.data.DataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Default implementation of {@link ColumnBuilder}
 *
 * @see ColumnBuilder
 */
public class DefaultColumnBuilder implements ColumnBuilder {

    private String name;
    private DataType type;
    private final List<Object> data;

    public DefaultColumnBuilder() {
        this.data = new ArrayList<>();
    }

    @Override
    public ColumnBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ColumnBuilder type(DataType type) {
        this.type = type;
        return this;
    }

    @Override
    public ColumnBuilder add(Object value) {
        data.add(value);
        return this;
    }

    @Override
    public ColumnBuilder addAll(Object[] values) {
        data.addAll(Arrays.asList(values));
        return this;
    }

    @Override
    public ColumnBuilder addAll(Iterable<?> values) {
        values.forEach(data::add);
        return this;
    }

    @Override
    public Column build() {
        if (type == null) {
            throw new IllegalStateException("Type must be set before building");
        }

        return ColumnFactory.create(name, data.toArray(), type);
    }
}
