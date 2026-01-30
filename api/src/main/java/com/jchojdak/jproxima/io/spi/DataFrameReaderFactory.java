package com.jchojdak.jproxima.io.spi;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.io.DataFrameReader;
import com.jchojdak.jproxima.io.read.CsvReaderBuilder;
import com.jchojdak.jproxima.io.read.ExcelReaderBuilder;

import java.nio.file.Path;

/**
 * <b>INTERNAL SPI - NOT FOR PUBLIC USE</b>
 * <p>
 * This is a Service Provider Interface for internal use only.
 * Developers should use {@link DataFrameReader} API instead.
 *
 * @see DataFrameReader
 */
public interface DataFrameReaderFactory {

    /**
     * Automatically detects the file type and parses the given file into a {@link DataFrame}.
     *
     * @param path path to the input file
     * @return parsed {@link DataFrame}
     * @throws IllegalArgumentException if the file type is not supported
     */
    DataFrame readAuto(Path path);

    /**
     * Creates a {@link CsvReaderBuilder} for the given CSV file.
     *
     * @param path path to the CSV file
     * @return CSV reader builder instance
     */
    CsvReaderBuilder createCsvReader(Path path);

    /**
     * Creates an {@link ExcelReaderBuilder} for the given Excel file.
     *
     * @param path path to the Excel file
     * @return Excel reader builder instance
     */
    ExcelReaderBuilder createExcelReader(Path path);
}
