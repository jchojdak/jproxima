package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.Column;
import com.jchojdak.jproxima.data.IntColumnBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseColumnTest {

    @Test
    void shouldReturnCorrectColumnEquals() {
        Column col1 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .add(2)
                .add(3)
                .build();

        Column col2 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .add(2)
                .add(3)
                .build();

        assertEquals(col1, col2);
    }

    @Test
    void shouldReturnCorrectColumnHashCode() {
        Column col1 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .add(2)
                .add(3)
                .build();

        Column col2 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .add(2)
                .add(3)
                .build();

        assertEquals(col1.hashCode(), col2.hashCode());
    }
}
