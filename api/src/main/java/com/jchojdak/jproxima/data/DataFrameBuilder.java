package com.jchojdak.jproxima.data;

import java.util.ServiceLoader;

public interface DataFrameBuilder {

    DataFrameBuilder addColumn(String name, Object[] data, DataType type);

    DataFrame build();

    static DataFrameBuilder create() {
        ServiceLoader<DataFrameBuilder> loader = ServiceLoader.load(DataFrameBuilder.class);
        return loader.findFirst()
                .orElseThrow(() -> new IllegalStateException("No DataFrameBuilder implementation found"));
        /*
        try {
            Class<?> clazz = Class.forName("com.jchojdak.jproxima.impl.data.DefaultDataFrameBuilder");
            return (DataFrameBuilder) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create DataFrameBuilder implementation", e);
        }*/
    }
}
