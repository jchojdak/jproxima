package com.jchojdak.jproxima.data;

import java.util.ServiceLoader;

public interface DoubleColumnBuilder {

    DoubleColumnBuilder name(String name);

    DoubleColumnBuilder add(double value);

    DoubleColumnBuilder add(double[] values);

    DoubleColumnBuilder addNull();

    DoubleColumn build();

    static DoubleColumnBuilder init() {
        ServiceLoader<DoubleColumnBuilder> loader = ServiceLoader.load(DoubleColumnBuilder.class);
        return loader.findFirst()
                .orElseThrow(() -> new IllegalStateException("No DoubleColumnBuilder implementation found"));
    }
}