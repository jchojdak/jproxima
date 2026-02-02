package com.jchojdak.jproxima.impl.io.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Estimates the number of rows in delimited text files (CSV etc.) for optimal memory pre-allocation.
 */
final class RowCountEstimator {

    /**
     * Minimum capacity returned by estimation.
     * Used for very small files (e.g. 10KB) and as a lower bound.
     */
    private static final int MIN_CAPACITY = 100;

    /**
     * Maximum capacity returned by estimation.
     * Prevents excessive memory allocation for extremely large files and protects against OutOfMemoryError.
     */

    private static final int MAX_CAPACITY = 10_000_000;

    /**
     * Safety margin.
     * 1.2 = 20%
     */
    private static final double SAFETY_MARGIN = 1.2;

    /**
     * Assumed average number of bytes per row in typical CSV files.
     */
    private static final int AVG_BYTES_PER_ROW = 200;

    /**
     * File size threshold (10KB) below which minimum capacity is always returned.
     * Avoids over-allocation for tiny files.
     */
    private static final long SMALL_FILE_THRESHOLD = 10_000;

    /**
     * Estimates the number of rows in a delimited text file.
     * <p>
     * This method uses file size as a proxy for row count, assuming an average row length of 200 bytes.
     * 20% safety margin is added to reduce the probability of ArrayList resizing during actual parsing.
     * Note: This method only reads file metadata (size) and don't open or parse the file contents, making it extremely fast.
     * <p>
     * Examples:
     * <ul>
     *     <li>5 KB file -> 100 rows (minimum)</li>
     *     <li>1 MB file -> ~6_000 rows</li>
     *     <li>100 MB file -> ~600_000 rows</li>
     *     <li>1 GB file -> ~6_000_000 rows</li>
     *     <li>10 GB file -> 10_000_000 rows (capped at maximum)</li>
     * </ul>
     *
     * @param path path to the file
     * @return estimated row count
     * @throws IOException if the file size cannot be determined (e.g. file does not exist)
     */
    int estimate(Path path) throws IOException {
        long fileSizeBytes = Files.size(path);

        if (fileSizeBytes < SMALL_FILE_THRESHOLD) {
            return MIN_CAPACITY;
        }

        long estimatedRows = fileSizeBytes / AVG_BYTES_PER_ROW;
        return applyCapacityBounds(estimatedRows);
    }

    /**
     * Applies safety margin and capacity bounds to the raw row estimate.
     *
     * @param estimatedRows raw estimated row count before bounds are applied
     * @return bounded capacity value suitable for ArrayList initialization
     */
    private int applyCapacityBounds(long estimatedRows) {
        long withMargin = (long) (estimatedRows * SAFETY_MARGIN);
        int capacity = (int) Math.min(withMargin, MAX_CAPACITY);
        return Math.max(capacity, MIN_CAPACITY);
    }
}
