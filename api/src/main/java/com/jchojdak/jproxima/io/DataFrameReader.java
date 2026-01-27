package com.jchojdak.jproxima.io;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.io.read.CsvReaderBuilder;
import com.jchojdak.jproxima.io.read.ExcelReaderBuilder;
import com.jchojdak.jproxima.io.spi.DataFrameReaderFactory;

import java.nio.file.Path;
import java.util.ServiceLoader;

public interface DataFrameReader {

    static DataFrame read(String path) {
        return read(Path.of(path));
    }

    static DataFrame read(Path path) {
        return getReaderFactory().readAuto(path);
    }

    static CsvReaderBuilder csv(String path) {
        return csv(Path.of(path));
    }

    static CsvReaderBuilder csv(Path path) {
        return getReaderFactory().createCsvReader(path);
    }

    static ExcelReaderBuilder excel(String path) {
        return excel(Path.of(path));
    }

    static ExcelReaderBuilder excel(Path path) {
        return getReaderFactory().createExcelReader(path);
    }

    private static DataFrameReaderFactory getReaderFactory() {
        ServiceLoader<DataFrameReaderFactory> loader = ServiceLoader.load(DataFrameReaderFactory.class);
        return loader.findFirst()
                .orElseThrow(() -> new IllegalStateException("No DataFrameReaderFactory implementation found"));
    }
}