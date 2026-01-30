package com.jchojdak.jproxima.impl.io;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.impl.io.csv.DefaultCsvReaderBuilder;
import com.jchojdak.jproxima.io.read.CsvReaderBuilder;
import com.jchojdak.jproxima.io.read.ExcelReaderBuilder;
import com.jchojdak.jproxima.io.spi.DataFrameReaderFactory;

import java.nio.file.Path;

/**
 * Default implementation of {@link DataFrameReaderFactory}.
 * <p>
 * File type detection is currently based on file name extensions.
 */
public final class DefaultDataFrameReaderFactory implements DataFrameReaderFactory {

    @Override
    public DataFrame readAuto(Path path) {
        // TODO: refactor this "if" into the strategy pattern
        if (path.toString().endsWith(".csv")) {
            return createCsvReader(path).build();
        }
        if (path.toString().endsWith(".xlsx")) {
            return createExcelReader(path).build();
        }
        throw new IllegalArgumentException("Unsupported file type");
    }

    @Override
    public CsvReaderBuilder createCsvReader(Path path) {
        return new DefaultCsvReaderBuilder(path);
    }

    @Override
    public ExcelReaderBuilder createExcelReader(Path path) {
        // TODO: implement
        //return new DefaultExcelReaderBuilder(path);
        throw new UnsupportedOperationException("Excel reader not implemented yet");
    }
}
