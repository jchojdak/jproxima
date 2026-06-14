package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.Column;
import com.jchojdak.jproxima.data.IntColumnBuilder;
import com.jchojdak.jproxima.data.StringColumnBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BaseColumnTest {

    @Test
    void shouldReturnTrueWhenColumnsAreEqual() {
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
    void shouldReturnTrueWhenSameReference() {
        Column col1 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .add(2)
                .add(3)
                .build();

        assertEquals(col1, col1);
    }

    @Test
    void shouldReturnFalseWhenComparedToNull() {
        Column col1 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .build();

        assertNotEquals(null, col1);
    }

    @Test
    void shouldReturnFalseWhenNamesAreDifferent() {
        Column col1 = IntColumnBuilder.init()
                .name("col1")
                .add(1)
                .add(2)
                .add(3)
                .build();

        Column col2 = IntColumnBuilder.init()
                .name("col2")
                .add(1)
                .add(2)
                .add(3)
                .build();

        assertNotEquals(col1, col2);
    }

    @Test
    void shouldReturnFalseWhenTypesAreDifferent() {
        Column col1 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .build();

        Column col2 = StringColumnBuilder.init()
                .name("col")
                .add(new String[]{"1"})
                .build();

        assertNotEquals(col1, col2);
    }

    @Test
    void shouldReturnFalseWhenSizesAreDifferent() {
        Column col1 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .add(2)
                .build();

        Column col2 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .build();

        assertNotEquals(col1, col2);
    }

    @Test
    void shouldReturnFalseWhenValuesAreDifferent() {
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
                .add(99)
                .build();

        assertNotEquals(col1, col2);
    }

    @Test
    void shouldReturnTrueWhenBothColumnsHaveNullAtSameIndex() {
        Column col1 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .addNull()
                .add(3)
                .build();

        Column col2 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .addNull()
                .add(3)
                .build();

        assertEquals(col1, col2);
    }

    @Test
    void shouldReturnFalseWhenOneColumnHasNullAndOtherDoesNot() {
        Column col1 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .addNull()
                .add(3)
                .build();

        Column col2 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .add(2)
                .add(3)
                .build();

        assertNotEquals(col1, col2);
    }

    @Test
    void shouldReturnSameHashCodeForEqualColumns() {
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

    @Test
    void shouldReturnSameHashCodeOnMultipleCalls() {
        Column col1 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .add(2)
                .add(3)
                .build();

        assertEquals(col1.hashCode(), col1.hashCode());
    }

    @Test
    void shouldReturnSameHashCodeForColumnsWithNulls() {
        Column col1 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .addNull()
                .add(3)
                .build();

        Column col2 = IntColumnBuilder.init()
                .name("col")
                .add(1)
                .addNull()
                .add(3)
                .build();

        assertEquals(col1.hashCode(), col2.hashCode());
    }
}
