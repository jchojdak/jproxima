package com.jchojdak.jproxima.data;

import java.util.ServiceLoader;

public interface StringColumnBuilder {

    StringColumnBuilder name(String name);

    StringColumnBuilder add(String value);

    StringColumnBuilder add(String[] values);

    StringColumn build();

    static StringColumnBuilder init() {
        ServiceLoader<StringColumnBuilder> loader = ServiceLoader.load(StringColumnBuilder.class);
        return loader.findFirst()
                .orElseThrow(() -> new IllegalStateException("No StringColumnBuilder implementation found"));
    }
}