package com.jchojdak.jproxima.impl.data.stats;

import com.jchojdak.jproxima.data.ColumnStats;
import com.jchojdak.jproxima.data.IntColumn;
import com.jchojdak.jproxima.data.IntColumnBuilder;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IntColumnStatsTest {

    @Test
    void shouldReturnMinValue() {
        IntColumn column = IntColumnBuilder.init()
                .name("numbers")
                .add(new int[]{5, 2, 9, 1, 7})
                .build();

        ColumnStats stats = new IntColumnStats(column);

        assertEquals(1, stats.min());
    }

    @Test
    void shouldReturnMaxValue() {
        IntColumn column = IntColumnBuilder.init()
                .name("numbers")
                .add(new int[]{5, 2, 9, 1, 7})
                .build();

        ColumnStats stats = new IntColumnStats(column);

        assertEquals(9, stats.max());
    }

    @Test
    void shouldReturnAverageValue() {
        IntColumn column = IntColumnBuilder.init()
                .name("numbers")
                .add(new int[]{1, 2, 3, 4})
                .build();

        ColumnStats stats = new IntColumnStats(column);

        assertEquals(2.5, stats.avg());
    }

    @Test
    void shouldReturnSumValue() {
        IntColumn column = IntColumnBuilder.init()
                .name("numbers")
                .add(new int[]{1, 2, 3, 4})
                .build();

        ColumnStats stats = new IntColumnStats(column);

        assertEquals(10, stats.sum());
    }

    @Test
    void shouldReturnCountOfNonNullValues() {
        IntColumn column = IntColumnBuilder.init()
                .name("numbers")
                .add(1)
                .addNull()
                .add(3)
                .addNull()
                .add(5)
                .build();

        ColumnStats stats = new IntColumnStats(column);

        assertEquals(3, stats.count());
    }

    @Test
    void shouldReturnCountOfNullValues() {
        IntColumn column = IntColumnBuilder.init()
                .name("numbers")
                .add(1)
                .addNull()
                .add(3)
                .addNull()
                .add(5)
                .build();

        ColumnStats stats = new IntColumnStats(column);

        assertEquals(2, stats.nullCount());
    }

    @Test
    void shouldThrowExceptionWhenCalculatingMinForEmptyColumn() {
        IntColumn column = IntColumnBuilder.init()
                .name("numbers")
                .addNull()
                .addNull()
                .build();

        ColumnStats stats = new IntColumnStats(column);

        assertThrows(IllegalStateException.class, stats::min);
    }

    @Test
    void shouldThrowExceptionWhenCalculatingMaxForEmptyColumn() {
        IntColumn column = IntColumnBuilder.init()
                .name("numbers")
                .addNull()
                .addNull()
                .build();

        ColumnStats stats = new IntColumnStats(column);

        assertThrows(IllegalStateException.class, stats::max);
    }

    @Test
    void shouldThrowExceptionWhenCalculatingAverageForEmptyColumn() {
        IntColumn column = IntColumnBuilder.init()
                .name("numbers")
                .addNull()
                .addNull()
                .build();

        ColumnStats stats = new IntColumnStats(column);

        assertThrows(IllegalStateException.class, stats::avg);
    }

    @Test
    void shouldReturnConsistentResultsUnderConcurrentAccess() throws InterruptedException {
        IntColumn column = IntColumnBuilder.init()
                .name("numbers")
                .add(new int[]{5, 2, 9, 1, 7})
                .build();

        ColumnStats stats = new IntColumnStats(column);

        int threadCount = 20;
        CountDownLatch ready = new CountDownLatch(threadCount);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threadCount);

        double[] minResults = new double[threadCount];
        double[] maxResults = new double[threadCount];

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            new Thread(() -> {
                ready.countDown();
                try {
                    start.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                minResults[idx] = stats.min();
                maxResults[idx] = stats.max();
                done.countDown();
            }).start();
        }

        ready.await();
        start.countDown();
        done.await();

        for (int i = 0; i < threadCount; i++) {
            assertEquals(1.0, minResults[i], "min mismatch at thread " + i);
            assertEquals(9.0, maxResults[i], "max mismatch at thread " + i);
        }
    }
}
