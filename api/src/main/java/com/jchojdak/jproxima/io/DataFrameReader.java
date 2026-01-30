package com.jchojdak.jproxima.io;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.io.read.CsvReaderBuilder;
import com.jchojdak.jproxima.io.read.ExcelReaderBuilder;
import com.jchojdak.jproxima.io.spi.DataFrameReaderFactory;

import java.nio.file.Path;
import java.util.ServiceLoader;

/**
 * Public entry point (facade) for reading tabular data into {@link DataFrame} instances.
 * <p>
 * This interface provides static factory for:
 * <ul>
 *     <li>Automatically detecting file type and reading data from various file formats.</li>
 *     <li>Creating specific file format readers (e.g., CSV, Excel).</li>
 * </ul>
 *
 * @see DataFrame
 * @see CsvReaderBuilder
 * @see ExcelReaderBuilder
 */
public interface DataFrameReader {

    /**
     * Reads a file from the given path string and automatically detects the file format.
     *
     * @param path path to the input file
     * @return parsed {@link DataFrame}
     */
    static DataFrame read(String path) {
        return read(Path.of(path));
    }

    /**
     * Reads a file from the given {@link Path} and automatically detects the file format.
     *
     * @param path path to the input file
     * @return parsed {@link DataFrame}
     */
    static DataFrame read(Path path) {
        return getReaderFactory().readAuto(path);
    }

    /**
     * Creates a {@link CsvReaderBuilder} for the given CSV file path string.
     *
     * @param path path to the CSV file
     * @return CSV reader builder instance
     */
    static CsvReaderBuilder csv(String path) {
        return csv(Path.of(path));
    }

    /**
     * Creates a {@link CsvReaderBuilder} for the given CSV file.
     *
     * @param path path to the CSV file
     * @return CSV reader builder instance
     */
    static CsvReaderBuilder csv(Path path) {
        return getReaderFactory().createCsvReader(path);
    }

    /**
     * Creates an {@link ExcelReaderBuilder} for the given Excel file path string.
     *
     * @param path path to the Excel file
     * @return Excel reader builder instance
     */
    static ExcelReaderBuilder excel(String path) {
        return excel(Path.of(path));
    }

    /**
     * Creates an {@link ExcelReaderBuilder} for the given Excel file.
     *
     * @param path path to the Excel file
     * @return Excel reader builder instance
     */
    static ExcelReaderBuilder excel(Path path) {
        return getReaderFactory().createExcelReader(path);
    }

    /**
     * Locates a {@link DataFrameReaderFactory} implementation using {@link ServiceLoader}.
     *
     * @return discovered {@link DataFrameReaderFactory} instance
     * @throws IllegalStateException if no implementation is found
     */
    private static DataFrameReaderFactory getReaderFactory() {
        ServiceLoader<DataFrameReaderFactory> loader = ServiceLoader.load(DataFrameReaderFactory.class);
        return loader.findFirst()
                .orElseThrow(() -> new IllegalStateException("No DataFrameReaderFactory implementation found"));
    }
}