package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Default implementation of {@link ColumnBuilder}
 *
 * @deprecated Use type-specific builders ({@link IntColumnBuilder}, {@link DoubleColumnBuilder},
 *             {@link BooleanColumnBuilder}, {@link StringColumnBuilder}) for zero-boxing performance
 *             and better memory efficiency.
 * @see ColumnBuilder
 * @see IntColumnBuilder
 * @see DoubleColumnBuilder
 * @see BooleanColumnBuilder
 * @see StringColumnBuilder
 */
@Deprecated(since = "0.2.0", forRemoval = true)
@SuppressWarnings({"removal", "deprecation"})
public final class DefaultColumnBuilder implements ColumnBuilder {

    private String name;
    private DataType type;
    private final List<Object> data;

    public DefaultColumnBuilder() {
        this.data = new ArrayList<>();
    }

    @Override
    @Deprecated(since = "0.2.0")
    public ColumnBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    @Deprecated(since = "0.2.0")
    public ColumnBuilder type(DataType type) {
        this.type = type;
        return this;
    }

    @Override
    @Deprecated(since = "0.2.0")
    public ColumnBuilder add(Object value) {
        data.add(value);
        return this;
    }

    @Override
    @Deprecated(since = "0.2.0")
    public ColumnBuilder addAll(Object[] values) {
        data.addAll(Arrays.asList(values));
        return this;
    }

    @Override
    @Deprecated(since = "0.2.0")
    public ColumnBuilder addAll(Iterable<?> values) {
        values.forEach(data::add);
        return this;
    }

    @Override
    @Deprecated(since = "0.2.0")
    public Column build() {
        if (type == null) {
            throw new IllegalStateException("Type must be set before building");
        }

        return ColumnFactory.create(name, data.toArray(), type);
    }
}
