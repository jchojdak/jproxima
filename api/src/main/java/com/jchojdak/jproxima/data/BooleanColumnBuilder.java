package com.jchojdak.jproxima.data;

import java.util.ServiceLoader;

public interface BooleanColumnBuilder {

    BooleanColumnBuilder name(String name);

    BooleanColumnBuilder add(boolean value);

    BooleanColumnBuilder add(boolean[] values);

    BooleanColumnBuilder addNull();

    BooleanColumn build();

    static BooleanColumnBuilder init() {
        ServiceLoader<BooleanColumnBuilder> loader = ServiceLoader.load(BooleanColumnBuilder.class);
        return loader.findFirst()
                .orElseThrow(() -> new IllegalStateException("No BooleanColumnBuilder implementation found"));
    }
}