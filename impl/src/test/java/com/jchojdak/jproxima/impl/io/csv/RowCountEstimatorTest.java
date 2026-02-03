package com.jchojdak.jproxima.impl.io.csv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RowCountEstimatorTest {

    private final RowCountEstimator estimator = new RowCountEstimator();

    @TempDir
    Path tempDir;

    @Test
    void shouldReturnMinCapacityWhenFileIsSmallerThanSmallFileThreshold() throws IOException {
        Path file = createFileWithSize(5_000);

        assertEquals(100, estimator.estimate(file));
    }

    @Test
    void shouldEstimateRowsWithSafetyMarginWhenFileIsMediumSized() throws IOException {
        // 1_000_000 / 200 = 5_000
        // 5_000 * 1.2 = 6_000
        Path file = createFileWithSize(1_000_000);

        assertEquals(6_000, estimator.estimate(file));
    }

    @Test
    void shouldFallbackToMinCapacityWhenEstimatedRowsAreBelowMinimum() throws IOException {
        // 10_000 / 200 = 50
        // 50 * 1.2 = 60 -> fallback to 100
        Path file = createFileWithSize(10_000);

        assertEquals(100, estimator.estimate(file));
    }

    @Test
    void shouldCapCapacityAtMaxWhenFileIsExtremelyLarge() throws IOException {
        // 10 GB -> capped at MAX_CAPACITY
        Path file = createFileWithSize(10_000_000_000L);

        assertEquals(10_000_000, estimator.estimate(file));
    }

    @Test
    void shouldEstimateRowsCorrectlyWhenFileIsLargeButBelowMaxCapacity() throws IOException {
        // 100 MB
        // 100_000_000 / 200 = 500_000
        // 500_000 * 1.2 = 600_000
        Path file = createFileWithSize(100_000_000);

        assertEquals(600_000, estimator.estimate(file));
    }

    private Path createFileWithSize(long sizeInBytes) throws IOException {
        Path file = tempDir.resolve("file-" + sizeInBytes + ".csv");

        try (var channel = Files.newByteChannel(
                file,
                java.nio.file.StandardOpenOption.CREATE_NEW,
                java.nio.file.StandardOpenOption.WRITE
        )) {
            channel.position(sizeInBytes);
            channel.write(java.nio.ByteBuffer.allocate(1));
        }

        return file;
    }
}
