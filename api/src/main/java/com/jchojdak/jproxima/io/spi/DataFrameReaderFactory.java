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
 * Developers should use {@link DataFrameReader} instead.
 */
public interface DataFrameReaderFactory {

    DataFrame readAuto(Path path);

    CsvReaderBuilder createCsvReader(Path path);

    ExcelReaderBuilder createExcelReader(Path path);
}
