package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.Column;
import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.DataFrameBuilder;
import com.jchojdak.jproxima.data.DataType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Default implementation of {@link DataFrameBuilder}
 *
 * @see DataFrameBuilder
 */
public class DefaultDataFrameBuilder implements DataFrameBuilder {

    private final Map<String, Column> columns;

    public DefaultDataFrameBuilder() {
        this.columns = new LinkedHashMap<>();
    }

    @Override
    public DataFrameBuilder addColumn(String name, Object[] data) {
        DataType type = DataType.from(data);
        return addColumn(new DefaultColumn(name, data, type));
    }

    @Override
    public DataFrameBuilder addColumn(String name, Object[] data, DataType type) {
        return addColumn(new DefaultColumn(name, data, type));
    }

    @Override
    public DataFrameBuilder addColumn(Column column) {
        columns.put(column.getName(), column);
        return this;
    }

    @Override
    public DataFrame build() {
        return new DefaultDataFrame(new LinkedHashMap<>(columns));
    }
}
