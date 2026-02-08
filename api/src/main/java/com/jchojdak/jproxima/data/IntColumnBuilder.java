package com.jchojdak.jproxima.data;

import java.util.ServiceLoader;

public interface IntColumnBuilder {

    IntColumnBuilder name(String name);

    IntColumnBuilder add(int value);

    IntColumnBuilder add(int[] values);

    IntColumnBuilder addNull();

    IntColumn build();

    static IntColumnBuilder init() {
        ServiceLoader<IntColumnBuilder> loader = ServiceLoader.load(IntColumnBuilder.class);
        return loader.findFirst()
                .orElseThrow(() -> new IllegalStateException("No IntColumnBuilder implementation found"));
    }
}