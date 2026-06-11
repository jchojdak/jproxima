package com.jchojdak.jproxima.impl.data.stats;

import com.jchojdak.jproxima.data.ColumnStats;
import com.jchojdak.jproxima.data.DoubleColumn;
import com.jchojdak.jproxima.data.DoubleColumnBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DoubleColumnStatsTest {

    @Test
    void shouldReturnMinValue() {
        DoubleColumn column = DoubleColumnBuilder.init()
                .name("numbers")
                .add(new double[]{5.5, 2.2, 9.9, 1.1, 7.7})
                .build();

        ColumnStats stats = new DoubleColumnStats(column);

        assertEquals(1.1, stats.min());
    }

    @Test
    void shouldReturnMaxValue() {
        DoubleColumn column = DoubleColumnBuilder.init()
                .name("numbers")
                .add(new double[]{5.5, 2.2, 9.9, 1.1, 7.7})
                .build();

        ColumnStats stats = new DoubleColumnStats(column);

        assertEquals(9.9, stats.max());
    }

    @Test
    void shouldReturnAverageValue() {
        DoubleColumn column = DoubleColumnBuilder.init()
                .name("numbers")
                .add(new double[]{1.0, 2.0, 3.0, 4.0})
                .build();

        ColumnStats stats = new DoubleColumnStats(column);

        assertEquals(2.5, stats.avg());
    }

    @Test
    void shouldReturnSumValue() {
        DoubleColumn column = DoubleColumnBuilder.init()
                .name("numbers")
                .add(new double[]{1.0, 2.0, 3.0, 4.0})
                .build();

        ColumnStats stats = new DoubleColumnStats(column);

        assertEquals(10.0, stats.sum());
    }

    @Test
    void shouldReturnCountOfNonNullValues() {
        DoubleColumn column = DoubleColumnBuilder.init()
                .name("numbers")
                .add(1.0)
                .addNull()
                .add(3.0)
                .addNull()
                .add(5.0)
                .build();

        ColumnStats stats = new DoubleColumnStats(column);

        assertEquals(3, stats.count());
    }

    @Test
    void shouldReturnCountOfNullValues() {
        DoubleColumn column = DoubleColumnBuilder.init()
                .name("numbers")
                .add(1.0)
                .addNull()
                .add(3.0)
                .addNull()
                .add(5.0)
                .build();

        ColumnStats stats = new DoubleColumnStats(column);

        assertEquals(2, stats.nullCount());
    }

    @Test
    void shouldThrowExceptionWhenCalculatingMinForEmptyColumn() {
        DoubleColumn column = DoubleColumnBuilder.init()
                .name("numbers")
                .addNull()
                .addNull()
                .build();

        ColumnStats stats = new DoubleColumnStats(column);

        assertThrows(IllegalStateException.class, stats::min);
    }

    @Test
    void shouldThrowExceptionWhenCalculatingMaxForEmptyColumn() {
        DoubleColumn column = DoubleColumnBuilder.init()
                .name("numbers")
                .addNull()
                .addNull()
                .build();

        ColumnStats stats = new DoubleColumnStats(column);

        assertThrows(IllegalStateException.class, stats::max);
    }

    @Test
    void shouldThrowExceptionWhenCalculatingAverageForEmptyColumn() {
        DoubleColumn column = DoubleColumnBuilder.init()
                .name("numbers")
                .addNull()
                .addNull()
                .build();

        ColumnStats stats = new DoubleColumnStats(column);

        assertThrows(IllegalStateException.class, stats::avg);
    }
}
